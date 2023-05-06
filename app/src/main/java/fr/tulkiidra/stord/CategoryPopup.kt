package fr.tulkiidra.stord

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.tulkiidra.stord.adapter.CategoryCardAdapter

class CategoryPopup(private val adapter: CategoryCardAdapter, private val category: CategoryModel) : Dialog(adapter.context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_category_detail)

        /* Create everything  */
        setupComponant()
        setupCloseButton()
        setupDeleteButton()
        setupFavoriteButton()
    }

    private fun setupComponant() {
        findViewById<TextView>(R.id.popup_category_name).text = category.name
        findViewById<TextView>(R.id.popup_category_description).text = category.description
        val categoryImage = findViewById<ImageView>(R.id.popup_category_image)
        Glide.with(adapter.context).load(Uri.parse(category.imageUrl)).into(categoryImage)
    }

    private fun setupDeleteButton(){
        findViewById<ImageView>(R.id.popup_supress_category).setOnClickListener{
            dismiss()
        }
    }

    private fun setupCloseButton(){
        findViewById<ImageView>(R.id.popup_close_category).setOnClickListener{
            dismiss()
        }
    }

    private fun setupFavoriteButton(){
        val favButton = findViewById<ImageView>(R.id.popup_favorite_category)
        if (category.favorite) {
            favButton.setImageResource(R.drawable.home)
        } else {
            favButton.setImageResource(R.drawable.star)
        }

        favButton.setOnClickListener{
            category.favorite = !category.favorite
            if (category.favorite){
                favButton.setImageResource(R.drawable.home)
            } else {
                favButton.setImageResource(R.drawable.star)
            }
        }
    }
}