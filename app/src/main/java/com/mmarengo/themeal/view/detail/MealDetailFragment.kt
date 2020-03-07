package com.mmarengo.themeal.view.detail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        viewModel.dataIsLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            val visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            binding.detailProgressBar.visibility = visibility
        })

        viewModel.mealDetail.observe(viewLifecycleOwner, Observer { mealDetail ->
            bindDetail(mealDetail)
        })

        return binding.root
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
        binding.layoutDetail.visibility = View.VISIBLE
    }
}
