package com.mmarengo.themeal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mmarengo.themeal.model.Meal

class MealDetailViewModelFactory(private val meal: Meal) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailViewModel::class.java)) {
            return MealDetailViewModel(meal) as T
        }
        throw IllegalStateException("Unknown ViewModel class")
    }
}
