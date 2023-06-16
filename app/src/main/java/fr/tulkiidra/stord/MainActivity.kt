package fr.tulkiidra.stord

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.tulkiidra.stord.fragments.AddNewCategoryFragment
import fr.tulkiidra.stord.fragments.AddNewItemFragment
import fr.tulkiidra.stord.fragments.CategoryFragment
import fr.tulkiidra.stord.fragments.FavoriteFragment
import fr.tulkiidra.stord.fragments.ItemFragment


class MainActivity() : AppCompatActivity() {

    var lastFragment : String = "category"
    var lastItem : Int = 0
    var usID  : Int = 0
    override fun onCreate(savedInstanceStrate: Bundle?){
        super.onCreate(savedInstanceStrate)
        setContentView(R.layout.activity_main)
        makeTransaction(CategoryFragment(this))

        this.usID = intent.getIntExtra("userId", 0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val invisibleTextViewForMenu = findViewById<TextView>(R.id.add_new_invisible_tv)

        // nav bar
        val navigationBarView = findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        navigationBarView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.favorite_page -> {
                    this.lastFragment = "FavoriteFragment"
                    makeTransaction(fragment = FavoriteFragment(this))
                    Toast.makeText(this, "Favorites", Toast.LENGTH_LONG).show()
                }
                R.id.categories_page -> {
                    this.lastFragment = "CategoryFragment"
                    makeTransaction(fragment = CategoryFragment(this))
                    Toast.makeText(this, "Categories", Toast.LENGTH_LONG).show()
                }
                R.id.add_new_page -> {
                    showMenu(invisibleTextViewForMenu, this)
                }
                else -> {

                }
            }
            true
        }
    }

    private fun showMenu(view : View, context : MainActivity): Boolean {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.add_new_menu)
        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.add_new_item_menu -> {
                    this.lastFragment = "AddNewItemFragment"
                    makeTransaction(fragment = AddNewItemFragment(context = context))
                    Toast.makeText(context, "Add New Item", Toast.LENGTH_LONG).show()
                }

                R.id.add_new_category_menu -> {
                    this.lastFragment = "AddNewCategoryFragment"
                    makeTransaction(fragment = AddNewCategoryFragment(context = context))
                    Toast.makeText(context, "Add New Category", Toast.LENGTH_LONG).show()
                }
            }
            return@setOnMenuItemClickListener true
        }
        popup.show()
        return true
    }

    fun refresh(context: MainActivity){
        when (context.lastFragment) {
            "FavoriteFragment" -> makeTransaction(fragment = FavoriteFragment(context))
            "CategoryFragment" -> makeTransaction(fragment = CategoryFragment(context))
            "AddNewItemFragment" -> makeTransaction(fragment = AddNewItemFragment(context))
            "AddNewCategoryFragment" -> makeTransaction(fragment = AddNewCategoryFragment(context))
            "ItemFragment" -> makeTransaction(ItemFragment(context, context.lastItem))
            "" -> {}
        }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
