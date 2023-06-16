package fr.tulkiidra.stord

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.tulkiidra.stord.fragments.ApiTestFragment


class Login : AppCompatActivity() {
    val id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationBarView = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        navigationBarView.visibility = View.GONE

        makeTransaction(ApiTestFragment(this))

    }
    fun makeTransaction(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack("new")
        transaction.commit()
    }

    fun visibleNavBar(){
        val navigationBarView = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        navigationBarView.visibility = View.VISIBLE
    }

    fun switchActivities(id: Int) {
        val switchActivityIntent = Intent(this, MainActivity::class.java)
        switchActivityIntent.putExtra("userId", id)
        startActivity(switchActivityIntent)
        finish()
    }
}