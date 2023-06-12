package fr.tulkiidra.stord.fragments

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
import java.util.concurrent.CompletableFuture

class AuthLoginFragment(
    private val context : MainActivity
) : Fragment() {
    private var body = mutableMapOf<String, String>()
    private var passwordShowing = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.authentification_login, container, false)

        val usernameET = view.findViewById<EditText>(R.id.username)
        val passwordET = view.findViewById<EditText>(R.id.password)
        val signInButton = view.findViewById<Button>(R.id.signInButton)
        val passwordIcon = view.findViewById<ImageView>(R.id.passwordIcon)
        val signInGoogleBtn = view.findViewById<RelativeLayout>(R.id.signInWithGoogle)
        val signUpBtn = view.findViewById<TextView>(R.id.signUp)

        passwordIcon.setOnClickListener {

            if (passwordShowing) {
                passwordET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordIcon.setImageResource(R.drawable.show)
            } else {
                passwordET.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordIcon.setImageResource(R.drawable.invisible)
            }
            passwordShowing = !passwordShowing
            passwordET.setSelection(passwordET.length())
        }

        signInButton.setOnClickListener {
            body["username"] = usernameET.text.toString()
            body["password"] = passwordET.text.toString()
            doLoginRequest()
            if (context.userId != 0){
                context.makeTransaction(CategoryFragment(context))
                context.visibleNavBar()
            }
        }

        signInGoogleBtn.setOnClickListener {  }

        signUpBtn.setOnClickListener { context.makeTransaction(AuthRegisterFragment(context = context)) }


        return view
    }

    private fun doLoginRequest(): Int {
        val completableFuture = CompletableFuture<Int>()
        CoroutineScope(Dispatchers.IO).launch {
            val item = async {
                getRequest()
            }
            completableFuture.complete(item.await())
        }
        return completableFuture.get()
    }

    private fun getRequest(): Int {
        // Make Request
        val client = OkHttpClient()
        val json = "application/json; charset=utf-8".toMediaTypeOrNull()
        val jsonObject = JSONObject()
        jsonObject.put("username", body["username"])
        jsonObject.put("password", body["password"])
        val bodyJ = jsonObject.toString().toRequestBody(json)
        val request = Request.Builder()
            .url("https://stord.tech/api/login")
            .post(bodyJ)
            .build()
        val responseBody = client.newCall(request).execute().body
        return responseBody.string().replace("{\"id\":", "").replace("}", "").toInt()
    }
}