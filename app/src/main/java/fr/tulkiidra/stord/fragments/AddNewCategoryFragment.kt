package fr.tulkiidra.stord.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import fr.tulkiidra.stord.CategoryModel
import fr.tulkiidra.stord.ItemModel
import fr.tulkiidra.stord.MainActivity
import fr.tulkiidra.stord.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID
import java.util.concurrent.CompletableFuture

class AddNewCategoryFragment(
    private val context : MainActivity,
) : Fragment() {
    private var file:Uri? = null
    private var fileLink = arrayListOf<String>()
    private var arrayList = arrayListOf<String>()
    private var arrayList1 = arrayListOf<CategoryModel>()
    private val body = mutableMapOf<String, Any>("name" to "Undefined", "description" to "Undefined", "imageURL" to "Undefined", "favorite" to 0, "owner_id" to "$context.userId", "categoryParent" to 0)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val firebaseStorageLink = "gs://stord-a70a6.appspot.com"
        val view = inflater.inflate(R.layout.add_new_category, container, false)
        val imageButton = view.findViewById<Button>(R.id.add_new_button)
        val imageItem = view.findViewById<ImageView>(R.id.add_new_button_image)
        val confirmButton = view.findViewById<Button>(R.id.add_new_confirm_button)
        val spinner = view.findViewById<Spinner>(R.id.spinner_category_input)
        arrayList1 = doNetworkCallsInParallel(context.userId)
        arrayList.add("No Parent")
        for (d in arrayList1){
            arrayList.add(d.name)
        }

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, arrayList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        // Connect firebase storage
        FirebaseStorage.getInstance().getReferenceFromUrl(firebaseStorageLink)
        // Image Setter
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null){
                file = uri
                uploadImage(file!!)
                imageItem.setImageURI(uri)
            }
        }
        // Image button
        imageButton.setOnClickListener { getContent.launch("image/*") }

        // Confirm button
        confirmButton.setOnClickListener { sendItem(view) }

        return view
    }

    private fun sendItem(view: View) {
        if (fileLink.size == 0){ fileLink.add("") }
        try {
            body["name"] = view.findViewById<EditText>(R.id.add_new_item_name_input).text.toString()
            body["description"] = view.findViewById<EditText>(R.id.add_new_item_description_input).text.toString()
            body["imageURL"] = fileLink[0].replace("\\", "")
            body["favorite"] = 0.toString()
            val name = view.findViewById<Spinner>(R.id.spinner_category_input).selectedItem.toString()
            if (name == "No Parent"){
                body["categoryParent"] = ""
            } else {
                for (i in arrayList1) {
                    if (i.name == name) {
                        body["categoryParent"] = i.id.toString()
                        break
                    }
                }
            }
            doNetworkCallsInParallelPut()
        } catch (e: Exception){throw e}
        context.makeTransaction(fragment = AddNewCategoryFragment(context))
        Toast.makeText(context,"Created", Toast.LENGTH_LONG).show()
    }


    private fun uploadImage(fileUri : Uri): String{
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val ref = Firebase.storage.reference.child(fileName)
        val uploadTask = ref.putFile(fileUri)

        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful){
                fileLink.add("null")
                task.exception?.let { throw it }
            }
            return@Continuation ref.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val downloadURI = task.result
                fileLink.add(downloadURI.toString())
                Log.d("Name", fileLink[0])
            }
        }
        return "null"
    }
    private fun doNetworkCallsInParallelPut(): ArrayList<ItemModel> {
        val completableFuture = CompletableFuture<ArrayList<ItemModel>>()
        CoroutineScope(Dispatchers.IO).launch {
            val list = async {
                putRequest()
            }
            completableFuture.complete(list.await())
        }
        return completableFuture.get()
    }

    private fun putRequest(): ArrayList<ItemModel>{
        val client = OkHttpClient()
        val json = "application/json; charset=utf-8".toMediaTypeOrNull()
        val jsonObject = JSONObject()
        jsonObject.put("name", body["name"])
        jsonObject.put("description", body["description"])
        jsonObject.put("imageURL", body["imageURL"])
        jsonObject.put("favorite", body["favorite"])
        jsonObject.put("parent_category_id", body["categoryParent"])
        jsonObject.put("owner_id", body["owner_id"])
        val url = "https://stord.tech/api/create/category"
        val bodyJ = jsonObject.toString().toRequestBody(json)
        val newReq = Request.Builder()
            .url(url)
            .post(bodyJ)
            .build()
        client.newCall(newReq).execute().body
        return ArrayList()
    }

    private fun doNetworkCallsInParallel(user_id: Int): ArrayList<CategoryModel> {
        val completableFuture = CompletableFuture<ArrayList<CategoryModel>>()
        CoroutineScope(Dispatchers.IO).launch {
            val list = async {
                getRequest(user_id)
            }
            completableFuture.complete(list.await())
        }
        return completableFuture.get()
    }

    private fun getRequest(user_id: Int): ArrayList<CategoryModel> {
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