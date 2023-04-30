package fr.tulkiidra.stord.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.tulkiidra.stord.ItemModel
import fr.tulkiidra.stord.MainActivity
import fr.tulkiidra.stord.R
import fr.tulkiidra.stord.adapter.CategoryDecoration
import fr.tulkiidra.stord.adapter.ItemCardAdapter

class FavoriteFragment(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val itemList = arrayListOf<ItemModel>()

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

        if (itemList.isEmpty()) {
            return inflater.inflate(R.layout.empty_favorite_item, container, false)
        }
        val view = inflater.inflate(R.layout.favorite_item, container, false)

        val favoriteRecycler = view?.findViewById<RecyclerView>(R.id.favorite_list)
        favoriteRecycler?.adapter = ItemCardAdapter(context, itemList, R.layout.item_long_card)
        favoriteRecycler?.addItemDecoration(CategoryDecoration())

        return view
    }
}