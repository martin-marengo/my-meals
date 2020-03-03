package com.mmarengo.myrecipe.view.meals

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mmarengo.myrecipe.R
import com.mmarengo.myrecipe.databinding.FragmentMealsSearchBinding
import com.mmarengo.myrecipe.model.Meal
import com.mmarengo.myrecipe.viewmodel.MealsViewModel

class MealsSearchFragment : Fragment() {

    private val viewModel: MealsViewModel by viewModels()
    private lateinit var binding: FragmentMealsSearchBinding
    private lateinit var adapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_meals_search,
            container,
            false)

        setUpMealList()
        setUpObservers()

        viewModel.currentSearch?.let { binding.mealsSearchEdit.setText(it) }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setUpSearchBox()
    }

    private fun setUpObservers() {
        viewModel.dataIsLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.mealsProgressBar.visibility =
                if (isLoading) View.VISIBLE else View.INVISIBLE
        })

        viewModel.eventError.observe(viewLifecycleOwner, Observer { strResId ->
            context?.let { context ->
                Snackbar.make(
                    binding.root,
                    context.resources.getString(strResId),
                    Snackbar.LENGTH_SHORT
                ).apply {
                    show()
                }
            }
        })

        viewModel.dataMeals.observe(viewLifecycleOwner, Observer { mealList ->
            adapter.submitList(mealList)
            // Set image for empty result.
        })

        viewModel.eventMealTapped.observe(viewLifecycleOwner, Observer { meal ->
            meal?.let {
                onMealTapped(meal)
                viewModel.onMealTappedFinished()
            }
        })
    }

    private fun setUpSearchBox() {
        binding.mealsSearchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchMeals(p0.toString())
            }
        })
    }

    private fun setUpMealList() {
        context?.let { context ->
            val layoutManager = LinearLayoutManager(context)
            binding.mealsRecycler.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(
                binding.mealsRecycler.context,
                layoutManager.orientation)
            binding.mealsRecycler.addItemDecoration(dividerItemDecoration)
        }

        adapter = MealAdapter { meal -> viewModel.onMealTapped(meal) }

        binding.mealsRecycler.adapter = adapter
    }

    private fun onMealTapped(meal: Meal) {
        val action = MealsSearchFragmentDirections.
            actionMealsSearchFragmentToMealDetailFragment(meal)
        Navigation.findNavController(binding.root).navigate(action)
    }
}
