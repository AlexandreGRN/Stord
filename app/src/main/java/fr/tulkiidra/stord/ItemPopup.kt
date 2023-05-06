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

        setupComponants()
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
    }
}