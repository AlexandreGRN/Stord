package fr.tulkiidra.stord

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.tulkiidra.stord.adapter.ItemCardAdapter

class ItemPopup(private val adapter: ItemCardAdapter, private val item: ItemModel) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_item_detail)

        /*  Create all the button/element in the popup  */

        // Create all the responsive element (image and text)
        setupComponants()

        // Create a button to close the popup
        setupCloseButton()

        // Create the button to delete an item
        setupDeleteButton()

        // Create a favorite button
        setupFavoriteButton()
    }

    private fun setupFavoriteButton(){
        val favButton = findViewById<ImageView>(R.id.popup_favorite_item)

        favButton.setOnClickListener{
            item.favorite = !item.favorite
            if (item.favorite) {
                favButton.setImageResource(R.drawable.home)
            } else {
                favButton.setImageResource(R.drawable.star)
            }
        }
    }
    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.popup_close_button).setOnClickListener{
            dismiss()
        }
    }

    private  fun setupDeleteButton(){
        findViewById<ImageView>(R.id.popup_supress_item).setOnClickListener{
            dismiss()
        }
    }
    private fun setupComponants() {
        // Image
        val itemImage = findViewById<ImageView>(R.id.popup_item_image)
        Glide.with(adapter.context).load(Uri.parse(item.imageUrl)).into(itemImage)

        // Name
        findViewById<TextView>(R.id.popup_item_name).text = item.name

        // Description
        findViewById<TextView>(R.id.popup_item_description).text = item.description

        // Remaining
        findViewById<TextView>(R.id.popup_item_remaining).text = item.remaining.toString()

        // Alert
        findViewById<TextView>(R.id.popup_item_alert).text = item.alert.toString()

        // Favorite Logo
        val favButton = findViewById<ImageView>(R.id.popup_favorite_item)
        if (item.favorite) {
            favButton.setImageResource(R.drawable.home)
        } else {
            favButton.setImageResource(R.drawable.star)
        }
    }
}