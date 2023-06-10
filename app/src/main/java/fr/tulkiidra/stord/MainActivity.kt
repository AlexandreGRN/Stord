package fr.tulkiidra.stord

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.tulkiidra.stord.fragments.AddNewFragment
import fr.tulkiidra.stord.fragments.CategoryFragment
import fr.tulkiidra.stord.fragments.FavoriteFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceStrate: Bundle?){
        super.onCreate(savedInstanceStrate)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        makeTransaction(CategoryFragment(this, 1))

        // nav bar
        val navigationBarView = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        navigationBarView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.favorite_page -> {
                    Toast.makeText(this, "Favorites", Toast.LENGTH_LONG).show()
                    makeTransaction(fragment = FavoriteFragment(this, 1))
                }
                R.id.categories_page -> {
                    Toast.makeText(this, "Categories", Toast.LENGTH_LONG).show()
                    makeTransaction(fragment = CategoryFragment(this, 1))
                }
                R.id.add_new_page -> {
                    Toast.makeText(this, "Add New", Toast.LENGTH_LONG).show()
                    makeTransaction(fragment = AddNewFragment())
                }
                else -> {

                }
            }
            true
        }
    }

    fun makeTransaction(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack("new")
        transaction.commit()
    }

    public override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return true;
    }
}
