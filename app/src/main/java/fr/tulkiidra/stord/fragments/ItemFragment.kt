package fr.tulkiidra.stord.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        categoryList.add(CategoryModel(
            name = "Outils",
            description = "outils voiture",
            imageUrl = "https://cdn.pixabay.com/photo/2023/04/24/10/16/architecture-7947727_960_720.jpg",
            favorite = false))
        categoryList.add(CategoryModel(
            name = "Voiture",
            description = "ça roule",
            imageUrl = "https://cdn.pixabay.com/photo/2016/07/24/21/01/thermometer-1539191_960_720.jpg",
            favorite = true))
        categoryList.add(CategoryModel(
            name = "Service réparation",
            description = "service voiture",
            imageUrl = "https://cdn.pixabay.com/photo/2016/11/29/09/32/auto-1868726_960_720.jpg",
            favorite = false))
        itemList.add(ItemModel(
            name = "Clé à molette",
            description = "Fait des trucs",
            imageUrl = "https://cdn.pixabay.com/photo/2016/11/29/09/32/auto-1868726_960_720.jpg",
            favorite = false))
        itemList.add(ItemModel(
            name = "Citroen",
            description = "C'est beau",
            imageUrl = "https://cdn.pixabay.com/photo/2016/11/29/09/32/auto-1868726_960_720.jpg",
            favorite = false))
        itemList.add(ItemModel(
            name = "Trombonne",
            description = "Je sais pas pourquoi c'est là",
            imageUrl = "https://cdn.pixabay.com/photo/2016/11/29/09/32/auto-1868726_960_720.jpg",
            favorite = false))

        val categoryPart = view?.findViewById<RecyclerView>(R.id.subcategoryRecyclerView)
        categoryPart?.adapter = CategoryCardAdapter(context, categoryList, R.layout.category_long_card)

        val itemPart = view?.findViewById<RecyclerView>(R.id.itemRecyclerView)
        itemPart?.adapter = ItemCardAdapter(context, itemList, R.layout.item_long_card)
        categoryPart?.addItemDecoration(CategoryDecoration())
        itemPart?.addItemDecoration(CategoryDecoration())
        return view
    }
}