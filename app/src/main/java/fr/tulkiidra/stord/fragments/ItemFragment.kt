package fr.tulkiidra.stord.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.tulkiidra.stord.CategoryModel
import fr.tulkiidra.stord.ItemModel
import fr.tulkiidra.stord.MainActivity
import fr.tulkiidra.stord.R
import fr.tulkiidra.stord.adapter.CategoryCardAdapter
import fr.tulkiidra.stord.adapter.CategoryDecoration
import fr.tulkiidra.stord.adapter.ItemCardAdapter

class ItemFragment(private val context: MainActivity) : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.items_and_subcategories, container, false)

        val categoryList = arrayListOf<CategoryModel>()
        val itemList = arrayListOf<ItemModel>()

        if (categoryList.isEmpty() and itemList.isEmpty()){
            return inflater.inflate(R.layout.empty_items_and_subcategories, container, false)
        }

        if (categoryList.isEmpty()){
            val textCategory = view?.findViewById<TextView>(R.id.item_category_title)
            textCategory?.isVisible = false
        } else {
            val categoryPart = view?.findViewById<RecyclerView>(R.id.subcategoryRecyclerView)
            categoryPart?.adapter = CategoryCardAdapter(context, categoryList, R.layout.category_long_card)
            categoryPart?.addItemDecoration(CategoryDecoration())
        }

        if (itemList.isEmpty()){
            val textItem = view?.findViewById<TextView>(R.id.item_items_title)
            textItem?.isVisible = false
        } else {
            val itemPart = view?.findViewById<RecyclerView>(R.id.itemRecyclerView)
            itemPart?.adapter = ItemCardAdapter(context, itemList, R.layout.item_long_card)
            itemPart?.addItemDecoration(CategoryDecoration())
        }

        return view
    }
}