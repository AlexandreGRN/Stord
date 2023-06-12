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
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.util.concurrent.CompletableFuture

class AuthLoginFragment(
    private val context : MainActivity
) : Fragment() {
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

        signInButton.setOnClickListener {  }

        signInGoogleBtn.setOnClickListener {  }

        signUpBtn.setOnClickListener { context.makeTransaction(AuthRegisterFragment(context = context)) }


        return view
    }

    private fun doLoginRequest(user_id: Int): Int {
        val completableFuture = CompletableFuture<Int>()
        CoroutineScope(Dispatchers.IO).launch {
            val item = async {
                getRequest(user_id)
            }
            completableFuture.complete(item.await())
        }
        return completableFuture.get()
    }

    private fun getRequest(user_id: Int): Int {
        // Make Request
        val itemList = arrayListOf<ItemModel>()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://stord.tech/api/register")
            .build()
        val responseBody = client.newCall(request).execute().body
        val jsonItem = JSONArray(responseBody)
        Log.d("HTTPLogin", responseBody.toString())
        Log.d("HTTPLogin", jsonItem.toString())
        return 0
    }
}