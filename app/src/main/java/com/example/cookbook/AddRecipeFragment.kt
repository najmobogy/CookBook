package com.example.cookbook

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar

class AddRecipeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_recipe, container, false)
        val nameInput = view.findViewById<EditText>(R.id.recipe_name_input)
        val ingredientsInput = view.findViewById<EditText>(R.id.recipe_ingredients_input)
        val instructionsInput = view.findViewById<EditText>(R.id.recipe_instructions_input)
        val ratingBar = view.findViewById<RatingBar>(R.id.recipe_rating)
        val sharedPreferences = requireContext().getSharedPreferences("recipes", Context.MODE_PRIVATE)

        view.findViewById<Button>(R.id.save_recipe_button).setOnClickListener {
            val recipeData = "${nameInput.text}|${ingredientsInput.text}|${instructionsInput.text}|${ratingBar.rating}"
            val existingRecipes = sharedPreferences.getString("recipes", "") ?: ""
            sharedPreferences.edit().putString("recipes", if (existingRecipes.isEmpty()) recipeData else "$existingRecipes|$recipeData").apply()
            (activity as MainActivity).replaceFragment(RecipeListFragment())
        }
        return view
    }
}