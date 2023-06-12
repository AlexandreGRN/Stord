package fr.tulkiidra.stord.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import fr.tulkiidra.stord.MainActivity
import fr.tulkiidra.stord.R

class AuthRegisterFragment(
    context : MainActivity
) : Fragment() {
    private var passwordShowing = false

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


        passwordIcon.setOnClickListener {

            if (passwordShowing) {
                // passwordET.setInputType(InputType.TYPE_CLASS_TEXT  InputType.TYPE_TEXT_VARIATION_PASSWORD)
                passwordET.inputType = InputType.TYPE_CLASS_TEXT
                passwordET.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordIcon.setImageResource(R.drawable.home)
            } else {
                passwordET.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordIcon.setImageResource(R.drawable.close)
            }
            passwordShowing = !passwordShowing
            passwordET.setSelection(passwordET.length())
        }

        confirmPasswordIcon.setOnClickListener {

            if (passwordShowing) {
                // passwordET.setInputType(InputType.TYPE_CLASS_TEXT  InputType.TYPE_TEXT_VARIATION_PASSWORD)
                passwordET.inputType = InputType.TYPE_CLASS_TEXT
                passwordET.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordIcon.setImageResource(R.drawable.home)
            } else {
                passwordET.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordIcon.setImageResource(R.drawable.close)
            }
            passwordShowing = !passwordShowing
            passwordET.setSelection(passwordET.length())
        }

        signUpButton.setOnClickListener { }

        signInButton.setOnClickListener { }

        return view
    }

}