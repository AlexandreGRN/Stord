package fr.tulkiidra.stord.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.tulkiidra.stord.ItemModel
import fr.tulkiidra.stord.Login
import fr.tulkiidra.stord.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.CompletableFuture

class ApiTestFragment(private var context: Login) : Fragment() {
    private var responseBody : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            doNetworkCallsInParallel()
        } catch (e: Exception){
            return inflater.inflate(R.layout.api_issue, container, false)
        }
        if (responseBody.contains("\"Status\":\"OK\"")){
            context.makeTransaction(AuthLoginFragment(context))
        } else {
            return inflater.inflate(R.layout.api_issue, container, false)
        }
        return null
    }

    private fun doNetworkCallsInParallel(): ArrayList<ItemModel> {
        val completableFuture = CompletableFuture<ArrayList<ItemModel>>()
        CoroutineScope(Dispatchers.IO).launch {
            val list = async {
                getRequest()
            }
            completableFuture.complete(list.await())
        }
        return completableFuture.get()
    }

    private fun getRequest(): ArrayList<ItemModel> {
        // Make Request
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://stord.tech/api/status")
            .build()
        try{
            responseBody = client.newCall(request).execute().body.string()
        } catch (e: Exception){
            responseBody = "Error"
        }

        return arrayListOf<ItemModel>()
    }
}