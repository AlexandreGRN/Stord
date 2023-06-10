package fr.tulkiidra.stord

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.tulkiidra.stord.adapter.ItemCardAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

class ItemPopup(
    private val adapter: ItemCardAdapter,
    private val item: ItemModel
) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_item_detail)

        /*  Create all the button/element in the popup  */

        // Create all the responsive element (image and text)
        setupComponants()

        // Create a button to close the popup
        setupCloseButton()

        // Create the button to delete an item
        setupDeleteButton()

        // Create a favorite button
        setupFavoriteButton()
    }

    private fun setupFavoriteButton(){
        val favButton = findViewById<ImageView>(R.id.popup_favorite_item)

        favButton.setOnClickListener{
            item.favorite = !item.favorite
            if (item.favorite) {
                doNetworkCallsInParallelPut(1)
                favButton.setImageResource(R.drawable.fav_star)
            } else {
                doNetworkCallsInParallelPut(0)
                favButton.setImageResource(R.drawable.unfav_star)
            }
        }
    }
    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.popup_close_button).setOnClickListener{
            dismiss()
        }
    }

    private  fun setupDeleteButton(){
        findViewById<ImageView>(R.id.popup_supress_item).setOnClickListener{
            dismiss()
            doNetworkCallsInParallelDelete(item.id)
        }
    }
    private fun setupComponants() {
        // Image
        val itemImage = findViewById<ImageView>(R.id.popup_item_image)
        Glide.with(adapter.context).load(Uri.parse(item.imageURL)).into(itemImage)

        // Name
        findViewById<TextView>(R.id.popup_item_name).text = item.name

        // Description
        findViewById<TextView>(R.id.popup_item_description).text = item.description

        // Remaining
        findViewById<TextView>(R.id.popup_item_remaining).text = item.remaining.toString()

        // Alert
        findViewById<TextView>(R.id.popup_item_alert).text = item.alert.toString()

        // Favorite Logo
        val favButton = findViewById<ImageView>(R.id.popup_favorite_item)
        if (item.favorite) {
            favButton.setImageResource(R.drawable.fav_star)
        } else {
            favButton.setImageResource(R.drawable.unfav_star)
        }
    }
    fun doNetworkCallsInParallelPut(target: Int): ArrayList<ItemModel> {
        val completableFuture = CompletableFuture<ArrayList<ItemModel>>()
        CoroutineScope(Dispatchers.IO).launch {
            val list = async {
                putRequest(target)
            }
            completableFuture.complete(list.await())
        }
        return completableFuture.get()
    }

    private fun doNetworkCallsInParallelDelete(id: Int): ArrayList<ItemModel> {
        val completableFuture = CompletableFuture<ArrayList<ItemModel>>()
        CoroutineScope(Dispatchers.IO).launch {
            val list = async {
                deleteRequest(id)
            }
            completableFuture.complete(list.await())
        }
        return completableFuture.get()
    }

    private fun putRequest(target: Int): ArrayList<ItemModel>{
        val client = OkHttpClient()
        val json = "application/json; charset=utf-8".toMediaTypeOrNull()
        val jsonObject = JSONObject()
        val url = "https://stord.tech/api/fav/item/$target/" + item.id
        val body = jsonObject.toString().toRequestBody(json)
        val newReq = Request.Builder()
            .url(url)
            .put(body)
            .build()
        client.newCall(newReq).execute().body
        return ArrayList()
    }

    private fun deleteRequest(id: Int): ArrayList<ItemModel>{
        val client = OkHttpClient()
        val url = "https://stord.tech/api/delete/item/$id"
        val newReq = Request.Builder()
            .url(url)
            .delete()
            .build()
        client.newCall(newReq).execute().body
        return ArrayList()
    }
}
