package com.example.cookbook

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecipeListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        sharedPreferences = requireContext().getSharedPreferences("recipes", Context.MODE_PRIVATE)
        updateList()

        view.findViewById<Button>(R.id.add_recipe_button).setOnClickListener {
            (activity as MainActivity).replaceFragment(AddRecipeFragment())
        }
        return view
    }

    private fun updateList() {
        val recipes = loadRecipes()
        recyclerView.adapter = RecipeAdapter(recipes) { recipe ->
            val fragment = RecipeDetailsFragment.newInstance(recipe)
            (activity as MainActivity).replaceFragment(fragment)
        }
    }

    private fun loadRecipes(): List<String> {
        val jsonString = sharedPreferences.getString("recipes", "")
        return if (jsonString.isNullOrEmpty()) emptyList() else jsonString.split("|")
    }
}