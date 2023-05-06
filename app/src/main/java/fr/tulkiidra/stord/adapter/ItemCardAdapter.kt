package fr.tulkiidra.stord.adapter

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

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentItem = itemList[position]

            if (holder.itemCardImage != null) {
                Glide.with(context).load(Uri.parse(currentItem.imageUrl))
                    .into(holder.itemCardImage)
            }
            holder.itemName?.text = currentItem.name
            holder.itemDescription?.text = currentItem.description

            if (currentItem.favorite) {
                holder.starIcon?.setImageResource(R.drawable.home)
            } else {
                holder.starIcon?.setImageResource(R.drawable.star)
            }

            // when item clicked
            holder.itemView.setOnClickListener{
                ItemPopup(this, currentItem).show()
            }
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

    }