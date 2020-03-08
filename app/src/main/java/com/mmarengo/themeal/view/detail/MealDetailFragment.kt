package com.mmarengo.themeal.view.detail

import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mmarengo.themeal.R
import com.mmarengo.themeal.databinding.FragmentMealDetailBinding
import com.mmarengo.themeal.model.Meal
import com.mmarengo.themeal.model.MealDetail
import com.mmarengo.themeal.viewmodel.MealDetailViewModel
import com.mmarengo.themeal.viewmodel.MealDetailViewModelFactory

class MealDetailFragment : Fragment() {

    private lateinit var binding: FragmentMealDetailBinding
    private lateinit var viewModel: MealDetailViewModel
    private lateinit var viewModelFactory: MealDetailViewModelFactory
    private lateinit var meal: Meal
    private var atLeastOneIngredient = false
    private val ingredientSeparator by lazy {
        context?.resources?.getString(R.string.ingredients_measure_separator) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_meal_detail,
            container,
            false)

        // val meal = MealDetailFragmentArgs.fromBundle(arguments!!).meal
        val args by navArgs<MealDetailFragmentArgs>()

        meal = args.meal
        bindMeal(meal)

        viewModelFactory = MealDetailViewModelFactory(meal)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MealDetailViewModel::class.java)

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.dataIsLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            val visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            binding.detailProgressBar.visibility = visibility
        })

        viewModel.mealDetail.observe(viewLifecycleOwner, Observer { mealDetail ->
            bindDetail(mealDetail)
        })
    }

    private fun bindMeal(meal: Meal) {
        binding.meal = meal

        if (meal.thumbUrl == null) {
            binding.mealImage.setImageResource(R.drawable.ic_broken_image)
        } else {
            val uri = Uri.parse(meal.thumbUrl)
            Glide.with(binding.mealImage.context)
                .load(uri)
                .error(R.drawable.ic_broken_image)
                .placeholder(R.drawable.loading_animation)
                .into(binding.mealImage)
        }
    }

    private fun bindDetail(mealDetail: MealDetail) {
        binding.instructionsText.text = mealDetail.instructions

        showIngredients(mealDetail, LayoutInflater.from(context))
        if (atLeastOneIngredient) {
            binding.ingredientsTitleText.visibility = View.VISIBLE
            binding.layoutIngredients.visibility = View.VISIBLE
        }

        binding.layoutDetail.visibility = View.VISIBLE
    }

    private fun addIngredient(name: String, measure: String?, inflater: LayoutInflater) {
        atLeastOneIngredient = true

        val ingredientView: View = inflater.inflate(
            R.layout.item_ingredient, binding.layoutIngredients, false)
        binding.layoutIngredients.addView(ingredientView)

        val ingredientTextView: TextView = ingredientView.findViewById(R.id.ingredient_text)

        var ingredientText = name
        measure?.let {
            if (it.trim().isNotEmpty()) { ingredientText += ingredientSeparator + it }
        }
        ingredientTextView.text = ingredientText
    }

    private fun showIngredients(mealDetail: MealDetail, inflater: LayoutInflater) {
        mealDetail.ingredient1?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure1, inflater)
        }
        mealDetail.ingredient2?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure2, inflater)
        }
        mealDetail.ingredient3?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure3, inflater)
        }
        mealDetail.ingredient4?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure4, inflater)
        }
        mealDetail.ingredient5?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure5, inflater)
        }
        mealDetail.ingredient6?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure6, inflater)
        }
        mealDetail.ingredient7?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure7, inflater)
        }
        mealDetail.ingredient8?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure8, inflater)
        }
        mealDetail.ingredient9?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure9, inflater)
        }
        mealDetail.ingredient10?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure10, inflater)
        }
        mealDetail.ingredient11?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure11, inflater)
        }
        mealDetail.ingredient12?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure12, inflater)
        }
        mealDetail.ingredient13?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure13, inflater)
        }
        mealDetail.ingredient14?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure14, inflater)
        }
        mealDetail.ingredient15?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure15, inflater)
        }
        mealDetail.ingredient16?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure16, inflater)
        }
        mealDetail.ingredient17?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure17, inflater)
        }
        mealDetail.ingredient18?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure18, inflater)
        }
        mealDetail.ingredient19?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure19, inflater)
        }
        mealDetail.ingredient20?.let {
            if (it.trim().isNotEmpty()) addIngredient(it, mealDetail.measure20, inflater)
        }
    }
}
