package fr.tulkiidra.stord.fragments

import android.app.Activity
import android.content.Intent
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
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import fr.tulkiidra.stord.Login
import fr.tulkiidra.stord.R
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

class AuthRegisterFragment(
    private val context: Login
) : Fragment() {
    private var body = mutableMapOf<String, String>()
    private var passwordShowing = false
    private var confirmPasswordShowing = false
    private var userId :Int = 0
    private lateinit var gsc : GoogleSignInClient
    private lateinit var gso : GoogleSignInOptions
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    private lateinit var errTxt : String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.authentification_register, container, false)

        val usernameET = view.findViewById<EditText>(R.id.username)
        val emailET = view.findViewById<EditText>(R.id.email)
        val passwordET = view.findViewById<EditText>(R.id.password)
        val confirmPasswordET = view.findViewById<EditText>(R.id.confirm_password)
        val passwordIcon = view.findViewById<ImageView>(R.id.passwordIcon)
        val confirmPasswordIcon = view.findViewById<ImageView>(R.id.confirm_password_icon)
        val signUpButton = view.findViewById<Button>(R.id.signUnButton)
        val signInButton = view.findViewById<TextView>(R.id.sign_in_btn)
        val signInInstead = view.findViewById<RelativeLayout>(R.id.signInInstead)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(context, gso)
        passwordIcon.setOnClickListener {

            if (passwordShowing) {
                passwordET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordIcon.setImageResource(R.drawable.invisible)
            } else {
                passwordET.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordIcon.setImageResource(R.drawable.show)
            }
            passwordShowing = !passwordShowing
            passwordET.setSelection(passwordET.length())
        }

        confirmPasswordIcon.setOnClickListener {

            if (confirmPasswordShowing) {
                confirmPasswordET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                confirmPasswordIcon.setImageResource(R.drawable.invisible)
            } else {
                confirmPasswordET.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                confirmPasswordIcon.setImageResource(R.drawable.show)
            }
            confirmPasswordShowing = !confirmPasswordShowing
            confirmPasswordET.setSelection(confirmPasswordET.length())
        }

        signUpButton.setOnClickListener {
            body["username"] = usernameET.text.toString()
            body["password"] = passwordET.text.toString()
            body["conPassword"] = confirmPasswordET.text.toString()
            body["email"] = emailET.text.toString()
            if (passwordET.text.toString() == confirmPasswordET.text.toString()){
                this.userId = doRegisterRequest()
                if (this.userId == -1){
                    view.findViewById<TextView>(R.id.errorTxtRegister).text = errTxt.replace("{\"Status\":\"", "").replace("\"}", "")
                } else if (this.userId != 0){
                    context.switchActivities(this.userId)
                }
            } else {
                Toast.makeText(context, "password and confirm password do not match", Toast.LENGTH_LONG).show()
            }
        }

        signInInstead.setOnClickListener { context.makeTransaction(AuthLoginFragment(context = context)) }

        signInButton.setOnClickListener { context.makeTransaction(AuthLoginFragment(context = context))}

        return view
    }

    private fun doRegisterRequest(): Int {
        val completableFuture = CompletableFuture<Int>()
        CoroutineScope(Dispatchers.IO).launch {
            val item = async {
                postRequest()
            }
            completableFuture.complete(item.await())
        }
        return completableFuture.get()
    }

    private fun postRequest(): Int {
        // Make Request
        val client = OkHttpClient()
        val json = "application/json; charset=utf-8".toMediaTypeOrNull()
        val jsonObject = JSONObject()
        jsonObject.put("username", body["username"])
        jsonObject.put("password", body["password"])
        jsonObject.put("email", body["email"])
        val bodyJ = jsonObject.toString().toRequestBody(json)
        val request = Request.Builder()
            .url("https://stord.tech/api/register")
            .post(bodyJ)
            .build()
        val responseBody = client.newCall(request).execute().body
        val strResponse = responseBody.string()
        return if (strResponse.contains("Status")){
            errTxt = strResponse
            -1
        } else {
            responseBody.string().replace("{\"id\":", "").replace("}", "").toInt()
        }
    }
}
