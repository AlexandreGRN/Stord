package fr.tulkiidra.stord

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import fr.tulkiidra.stord.MainActivity
import com.bumptech.glide.Glide
import fr.tulkiidra.stord.adapter.CategoryCardAdapter
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

class CategoryPopup(
    private val context: MainActivity,
    private val adapter: CategoryCardAdapter,
    private val category: CategoryModel
) : Dialog(adapter.context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_category_detail)

        /* Create everything  */
        setupComponant()
        setupCloseButton()
        setupDeleteButton()
        setupFavoriteButton()
    }

    private fun setupComponant() {
        findViewById<TextView>(R.id.popup_category_name).text = category.name
        findViewById<TextView>(R.id.popup_category_description).text = category.description
        val categoryImage = findViewById<ImageView>(R.id.popup_category_image)
        Glide.with(adapter.context).load(Uri.parse(category.imageURL)).into(categoryImage)
    }

    private fun setupDeleteButton(){
        findViewById<ImageView>(R.id.popup_supress_category).setOnClickListener{
            dismiss()
            doNetworkCallsInParallelDelete(category.id)
            context.refresh(context)
        }
    }

    private fun setupCloseButton(){
        findViewById<ImageView>(R.id.popup_close_category).setOnClickListener{
            dismiss()
        }
    }

    private fun setupFavoriteButton(){
        val favButton = findViewById<ImageView>(R.id.popup_favorite_category)
        if (category.favorite) {
            favButton.setImageResource(R.drawable.fav_star)
        } else {
            favButton.setImageResource(R.drawable.unfav_star)
        }

        favButton.setOnClickListener{
            category.favorite = !category.favorite
            if (category.favorite){
                doNetworkCallsInParallelPut(1)
                favButton.setImageResource(R.drawable.fav_star)
                context.refresh(context)
            } else {
                doNetworkCallsInParallelPut(0)
                favButton.setImageResource(R.drawable.unfav_star)
                context.refresh(context)
            }
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
        val url = "https://stord.tech/api/fav/category/$target/" + category.id
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
        val url = "https://stord.tech/api/delete/category/$id"
        val newReq = Request.Builder()
            .url(url)
            .delete()
            .build()
        client.newCall(newReq).execute().body
        return ArrayList()
    }
}
