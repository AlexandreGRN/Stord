package fr.tulkiidra.stord.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fr.tulkiidra.stord.ItemModel
import fr.tulkiidra.stord.MainActivity
import fr.tulkiidra.stord.R
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

class FavoriteFragment(
    private val context: MainActivity,
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val itemList = doNetworkCallsInParallel(context.userId)

        if (itemList.isEmpty()) {
            return inflater.inflate(R.layout.empty_favorite_item, container, false)
        }
        val view = inflater.inflate(R.layout.favorite_item, container, false)

        val favoriteRecycler = view?.findViewById<RecyclerView>(R.id.favorite_list)
        favoriteRecycler?.adapter = ItemCardAdapter(context, itemList, R.layout.item_long_card)
        favoriteRecycler?.addItemDecoration(CategoryDecoration())

        return view
    }

    private fun doNetworkCallsInParallel(user_id: Int): ArrayList<ItemModel> {
        val completableFuture = CompletableFuture<ArrayList<ItemModel>>()
        CoroutineScope(Dispatchers.IO).launch {
            val list = async {
                getRequest(user_id)
            }
            completableFuture.complete(list.await())
        }
        return completableFuture.get()
    }

    private fun getRequest(user_id: Int): ArrayList<ItemModel> {
        // Make Request
        val itemList = arrayListOf<ItemModel>()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://stord.tech/api/favorite/item/$user_id")
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
