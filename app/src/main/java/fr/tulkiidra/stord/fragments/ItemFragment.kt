package fr.tulkiidra.stord.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fr.tulkiidra.stord.CategoryModel
import fr.tulkiidra.stord.ItemModel
import fr.tulkiidra.stord.MainActivity
import fr.tulkiidra.stord.R
import fr.tulkiidra.stord.adapter.CategoryCardAdapter
import fr.tulkiidra.stord.adapter.CategoryDecoration
import fr.tulkiidra.stord.adapter.ItemCardAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

class ItemFragment(private val context: MainActivity, private val category_id: Int) : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.items_and_subcategories, container, false)

        val (categoryList, itemList) = doNetworkCallsInParallel(category_id)

        if (categoryList.isEmpty() and itemList.isEmpty()){
            return inflater.inflate(R.layout.empty_items_and_subcategories, container, false)
        }

        if (categoryList.isEmpty()){
            val textCategory = view?.findViewById<TextView>(R.id.item_category_title)
            textCategory?.isVisible = false
        } else {
            val categoryPart = view?.findViewById<RecyclerView>(R.id.subcategoryRecyclerView)
            categoryPart?.adapter = CategoryCardAdapter(context, categoryList, R.layout.category_long_card)
            categoryPart?.addItemDecoration(CategoryDecoration())
        }

        if (itemList.isEmpty()){
            val textItem = view?.findViewById<TextView>(R.id.item_items_title)
            textItem?.isVisible = false
        } else {
            val itemPart = view?.findViewById<RecyclerView>(R.id.itemRecyclerView)
            itemPart?.adapter = ItemCardAdapter(context, itemList, R.layout.item_long_card)
            itemPart?.addItemDecoration(CategoryDecoration())
        }

        return view
    }

    // This function transform a jsonString into a JsonList
    private fun doNetworkCallsInParallel(category_id: Int): Pair<ArrayList<CategoryModel>, ArrayList<ItemModel>> {
        val completableFuture = CompletableFuture<Pair<ArrayList<CategoryModel>, ArrayList<ItemModel>>>()
        CoroutineScope(Dispatchers.IO).launch {
            val listCategory = async {
                getRequestCategory(category_id)
            }
            val listItems = async {
                getRequestItem(category_id)
            }
            val categoryList = listCategory.await()
            val itemList = listItems.await()
            completableFuture.complete(categoryList to itemList)
        }
        return completableFuture.get()
    }
    private suspend fun getRequestCategory(category_id: Int): ArrayList<CategoryModel> {
        // Make Request
        val itemList = arrayListOf<CategoryModel>()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://stord.tech/api/categories/subcategories/$category_id")
            .build()
        val responseBody = client.newCall(request).execute().body
        val jsonList = convertJsonToList(responseBody.string())
        val mapper = jacksonObjectMapper()

        // Fill the lists
        for (i in jsonList) {
            val newItem: CategoryModel = mapper.readValue(i.toString())
            itemList.add(newItem)
        }
        return itemList
    }
    private suspend fun getRequestItem(category_id: Int): ArrayList<ItemModel> {
        // Make Request
        val itemList = arrayListOf<ItemModel>()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://stord.tech/api/items/$category_id")
            .build()
        val responseBody = client.newCall(request).execute().body
        val jsonList = convertJsonToList(responseBody.string())
        val mapper = jacksonObjectMapper()

        // Fill the lists
        for (i in jsonList) {
            val newItem: ItemModel = mapper.readValue(i.toString())
            itemList.add(newItem)
        }
        return itemList
    }


    // This function transform a jsonString into a JsonList
    private fun convertJsonToList(jsonString: String): List<JSONObject> {
        val jsonArray = JSONArray(jsonString)
        val jsonList = mutableListOf<JSONObject>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            jsonList.add(jsonObject)
        }

        return jsonList
    }
}
