package fr.tulkiidra.stord.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.tulkiidra.stord.ItemModel
import fr.tulkiidra.stord.ItemPopup
import fr.tulkiidra.stord.MainActivity
import fr.tulkiidra.stord.R

class ItemCardAdapter (
    val context: MainActivity,
    private val itemList: List<ItemModel>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<ItemCardAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val itemCardImage = view?.findViewById<ImageView>(R.id.item_card_image)
        val itemName = view?.findViewById<TextView>(R.id.name_item)
        val itemDescription = view?.findViewById<TextView>(R.id.description_item)
        val starIcon = view?.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.itemCardImage?.let {
            Glide.with(context).load(Uri.parse(currentItem.imageURL))
                .into(it)
        }

        try {
            holder.itemName?.text = currentItem.name
        } catch (e: Exception){
            holder.itemName?.text = "Unknown"
        }

        try {
            holder.itemDescription?.text = currentItem.description
        } catch (e: Exception){
            holder.itemDescription?.text = "Unknown"
        }


        if (currentItem.favorite) {
            holder.starIcon?.setImageResource(R.drawable.fav_star)
        } else {
            holder.starIcon?.setImageResource(R.drawable.unfav_star)
        }

        // when item clicked
        holder.itemView.setOnLongClickListener{
            ItemPopup(this, currentItem).show()
            return@setOnLongClickListener true
        }
        holder.starIcon?.setOnClickListener {
            if (currentItem.favorite){
                holder.starIcon?.setImageResource(R.drawable.unfav_star)
                ItemPopup(this, currentItem).doNetworkCallsInParallelPut(0)
            } else {
                holder.starIcon?.setImageResource(R.drawable.fav_star)
                ItemPopup(this, currentItem).doNetworkCallsInParallelPut(1)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}