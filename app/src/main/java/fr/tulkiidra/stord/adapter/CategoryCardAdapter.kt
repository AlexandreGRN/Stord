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
import fr.tulkiidra.stord.CategoryModel
import fr.tulkiidra.stord.CategoryPopup
import fr.tulkiidra.stord.MainActivity
import fr.tulkiidra.stord.R
import fr.tulkiidra.stord.fragments.ItemFragment

class CategoryCardAdapter(
    val context: MainActivity,
    private val categoryList: List<CategoryModel>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<CategoryCardAdapter.ViewHolder>(){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val categoryCardImage = view?.findViewById<ImageView>(R.id.category_card_image)
        val categoryName = view?.findViewById<TextView>(R.id.name_category)
        val categoryDescription = view?.findViewById<TextView>(R.id.description_category)
        val starIcon = view?.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCategory = categoryList[position]

        // Image
        holder.categoryCardImage?.let {
            Glide.with(context).load(Uri.parse(currentCategory.imageURL))
                .into(it)
        }

        // Name
        try {
            holder.categoryName?.text = currentCategory.name
        } catch (e: Exception){
            holder.categoryName?.text = "Unknown"
        }

        // Description
        try {
            holder.categoryDescription?.text = currentCategory.description
        } catch (e: Exception){
            holder.categoryDescription?.text = "Unknown"
        }


        if (currentCategory.favorite) {
            holder.starIcon?.setImageResource(R.drawable.fav_star)
        } else {
            holder.starIcon?.setImageResource(R.drawable.unfav_star)
        }

        holder.itemView.setOnLongClickListener{
            CategoryPopup(context, this, currentCategory).show()
            return@setOnLongClickListener true
        }
        holder.itemView.setOnClickListener{
            context.lastFragment = "ItemFragment"
            context.lastItem = currentCategory.id
            context.makeTransaction(ItemFragment(context, currentCategory.id))
        }

        holder.starIcon?.setOnClickListener {
            if (currentCategory.favorite){
                //holder.starIcon?.setImageResource(R.drawable.unfav_star)
                CategoryPopup(context, this, currentCategory).doNetworkCallsInParallelPut(0)
                context.refresh(context)
            } else {
                //holder.starIcon?.setImageResource(R.drawable.fav_star)
                CategoryPopup(context, this, currentCategory).doNetworkCallsInParallelPut(1)
                context.refresh(context)
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}