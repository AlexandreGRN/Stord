package fr.tulkiidra.stord.adapter

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCategory = categoryList[position]

        if (holder.categoryCardImage != null) {
            Glide.with(context).load(Uri.parse(currentCategory.imageURL))
                .into(holder.categoryCardImage)
        }
        holder.categoryName?.text = currentCategory.name
        holder.categoryDescription?.text = currentCategory.description

        if (currentCategory.favorite) {
            holder.starIcon?.setImageResource(R.drawable.home)
        } else {
            holder.starIcon?.setImageResource(R.drawable.star)
        }

        holder.itemView.setOnClickListener{
            CategoryPopup(this, currentCategory).show()
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}