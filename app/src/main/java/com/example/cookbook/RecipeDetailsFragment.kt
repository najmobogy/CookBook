package com.example.cookbook

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView

class RecipeDetailsFragment : Fragment() {
    companion object {
        fun newInstance(recipe: String): RecipeDetailsFragment {
            val fragment = RecipeDetailsFragment()
            val args = Bundle()
            val data = recipe.split("|")
            args.putString("name", data[0])
            args.putString("ingredients", data[1])
            args.putString("instructions", data[2])
            args.putFloat("rating", data[3].toFloat())
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)
        val name = view.findViewById<TextView>(R.id.recipe_name)
        val ingredients = view.findViewById<TextView>(R.id.recipe_ingredients)
        val instructions = view.findViewById<TextView>(R.id.recipe_instructions)
        val ratingBar = view.findViewById<RatingBar>(R.id.recipe_rating)
        val sharedPreferences = requireContext().getSharedPreferences("recipes", Context.MODE_PRIVATE)

        arguments?.let {
            name.text = it.getString("name")
            ingredients.text = it.getString("ingredients")
            instructions.text = it.getString("instructions")
            ratingBar.rating = it.getFloat("rating")
        }

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            val recipeName = arguments?.getString("name") ?: return@setOnRatingBarChangeListener
            val recipes = sharedPreferences.getString("recipes", "")?.split("|")?.toMutableList() ?: return@setOnRatingBarChangeListener
            val updatedRecipes = recipes.map {
                val data = it.split("|")
                if (data[0] == recipeName) "${data[0]}|${data[1]}|${data[2]}|$rating" else it
            }
            sharedPreferences.edit().putString("recipes", updatedRecipes.joinToString("|"))?.apply()
        }
        return view
    }
}