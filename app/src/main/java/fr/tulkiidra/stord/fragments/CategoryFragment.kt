package fr.tulkiidra.stord.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.tulkiidra.stord.CategoryModel
import fr.tulkiidra.stord.MainActivity
import fr.tulkiidra.stord.R
import fr.tulkiidra.stord.adapter.CategoryCardAdapter
import fr.tulkiidra.stord.adapter.CategoryDecoration

class CategoryFragment(
    private val context: MainActivity
) : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.category_list, container, false)

        val categoryList = arrayListOf<CategoryModel>()
        categoryList.add(CategoryModel(
            name = "Immobilier",
            description = "Possession d'appartement",
            //imageUrl = "https://cdn.pixabay.com/photo/2023/04/24/10/16/architecture-7947727_960_720.jpg",
            favorite = false
        ))
        categoryList.add(CategoryModel(
            name = "Medical",
            description = "Pillule en tout genre",
            //imageUrl = "https://cdn.pixabay.com/photo/2016/07/24/21/01/thermometer-1539191_960_720.jpg",
            favorite = false
        ))
        categoryList.add(CategoryModel(
            name = "Garage",
            description = "Voiture et outils",
            //imageUrl = "https://cdn.pixabay.com/photo/2016/11/29/09/32/auto-1868726_960_720.jpg",
            favorite = false
        ))

        val horizontalCategory = view?.findViewById<RecyclerView>(R.id.horizontalCategoryRecyclerView)
        horizontalCategory?.adapter = CategoryCardAdapter(context, categoryList, R.layout.category_big_card)

        val verticalCategory = view?.findViewById<RecyclerView>(R.id.verticalCategoryRecyclerView)
        verticalCategory?.adapter = CategoryCardAdapter(context, categoryList, R.layout.category_long_card)
        verticalCategory?.addItemDecoration(CategoryDecoration())

        return view
    }
}
