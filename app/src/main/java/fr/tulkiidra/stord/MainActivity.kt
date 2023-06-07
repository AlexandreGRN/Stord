package fr.tulkiidra.stord

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.tulkiidra.stord.fragments.CategoryFragment
import fr.tulkiidra.stord.fragments.FavoriteFragment
import fr.tulkiidra.stord.fragments.ItemFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceStrate: Bundle?){
        super.onCreate(savedInstanceStrate)
        setContentView(R.layout.activity_main)

        makeTransaction(CategoryFragment(this, 1))

        // nav bar
        val navigationBarView = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        navigationBarView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.favorite_page -> makeTransaction(fragment = FavoriteFragment(this, 1))
                R.id.categories_page -> makeTransaction(fragment = CategoryFragment(this, 1))
                R.id.add_new_page -> makeTransaction(fragment = ItemFragment(this, 3))
            }
            true
        }
    }

    private fun makeTransaction(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
