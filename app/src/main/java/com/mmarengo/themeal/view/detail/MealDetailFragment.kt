package com.mmarengo.themeal.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mmarengo.themeal.R
import com.mmarengo.themeal.databinding.FragmentMealDetailBinding

class MealDetailFragment : Fragment() {

    private lateinit var binding: FragmentMealDetailBinding

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

        val meal = MealDetailFragmentArgs.fromBundle(arguments!!).meal
        binding.mealText.text = meal.name

        return binding.root
    }
}
