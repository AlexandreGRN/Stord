package fr.tulkiidra.stord.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.tulkiidra.stord.R
import fr.tulkiidra.stord.adapter.CategoryCardAdapter
import fr.tulkiidra.stord.adapter.CategoryDecoration

class CategoryFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.category_list, container, false)

        val horizontalCategory = view?.findViewById<RecyclerView>(R.id.horizontalCategoryRecyclerView)
        horizontalCategory?.adapter = CategoryCardAdapter(R.layout.category_big_card)

        val verticalCategory = view?.findViewById<RecyclerView>(R.id.verticalCategoryRecyclerView)
        verticalCategory?.adapter = CategoryCardAdapter(R.layout.category_long_card)
        verticalCategory?.addItemDecoration(CategoryDecoration())

        return view
    }
}