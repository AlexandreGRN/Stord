package fr.tulkiidra.stord

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.tulkiidra.stord.fragments.AddNewFragment
import fr.tulkiidra.stord.fragments.CategoryFragment
import fr.tulkiidra.stord.fragments.FavoriteFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceStrate: Bundle?){
        super.onCreate(savedInstanceStrate)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        //transaction.replace(R.id.frame_container, CategoryFragment(this))
        transaction.replace(R.id.frame_container, FavoriteFragment(this))
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
