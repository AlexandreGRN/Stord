package fr.tulkiidra.stord.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fr.tulkiidra.stord.CategoryModel
import fr.tulkiidra.stord.MainActivity
import fr.tulkiidra.stord.R
import fr.tulkiidra.stord.adapter.CategoryCardAdapter
import fr.tulkiidra.stord.adapter.CategoryDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.CompletableFuture


class CategoryFragment(
    private val context: MainActivity,
    private val user_id: Int
) : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.category_list, container, false)

        val (categoryList, favCategoryList) = doNetworkCallsInParallel(user_id)

        // Create favorite part
        if (favCategoryList.isEmpty()){
            val textFavCategory = view?.findViewById<TextView>(R.id.category_favorite_title)
            textFavCategory?.isVisible = false
        }
        val verticalCategory = view?.findViewById<RecyclerView>(R.id.verticalCategoryRecyclerView)
        verticalCategory?.adapter = CategoryCardAdapter(context, categoryList, R.layout.category_long_card)
        verticalCategory?.addItemDecoration(CategoryDecoration())

        // Create full category part
        if (categoryList.isEmpty()){
            val textCategory = view?.findViewById<TextView>(R.id.category_category_title)
            textCategory?.isVisible = false
        }
        val horizontalCategory = view?.findViewById<RecyclerView>(R.id.horizontalCategoryRecyclerView)
        horizontalCategory?.adapter = CategoryCardAdapter(context, favCategoryList, R.layout.category_big_card)

        // When we don't have anything
        if (categoryList.isEmpty() and favCategoryList.isEmpty()){
            return inflater.inflate(R.layout.empty_category_list, container, false)
        }

        return view
    }

    private fun doNetworkCallsInParallel(user_id: Int): Pair<ArrayList<CategoryModel>, ArrayList<CategoryModel>> {
        val completableFuture = CompletableFuture<Pair<ArrayList<CategoryModel>, ArrayList<CategoryModel>>>()
        CoroutineScope(Dispatchers.IO).launch {
            val listCategory = async {
                getRequestCategory(user_id)
            }
            val listCategoryFav = async {
                getRequestCategoryFav(user_id)
            }
            val categoryList = listCategory.await()
            val categoryFavList = listCategoryFav.await()
            completableFuture.complete(categoryList to categoryFavList)
        }
        return completableFuture.get()
    }
    private fun getRequestCategory(user_id: Int): ArrayList<CategoryModel> {
        // Make Request
        val itemList = arrayListOf<CategoryModel>()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://stord.tech/api/categories/$user_id")
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
    private fun getRequestCategoryFav(user_id: Int): ArrayList<CategoryModel> {
        // Make Request
        val itemList = arrayListOf<CategoryModel>()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://stord.tech/api/favorite/category/$user_id")
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
