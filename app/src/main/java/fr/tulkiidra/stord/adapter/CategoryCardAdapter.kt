package fr.tulkiidra.stord.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import fr.tulkiidra.stord.R

class CategoryCardAdapter(private val layoutId: Int) : RecyclerView.Adapter<CategoryCardAdapter.ViewHolder>(){


    class ViewHolder(bigCardView: View) : RecyclerView.ViewHolder(bigCardView){
        val categoryCard = bigCardView.findViewById<ImageView>(R.id.category_big_card)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount(): Int {
        return 10
    }

}