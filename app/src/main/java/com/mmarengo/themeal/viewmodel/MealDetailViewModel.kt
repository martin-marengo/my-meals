package com.mmarengo.themeal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmarengo.themeal.model.Meal
import com.mmarengo.themeal.model.MealDetail
import com.mmarengo.themeal.repository.DataResponse
import com.mmarengo.themeal.repository.MealsRepository
import kotlinx.coroutines.launch

class MealDetailViewModel(private val meal: Meal) : ViewModel() {

    private val mealsRepository by lazy { MealsRepository() }

    private val _dataIsLoading = MutableLiveData<Boolean>()
    val dataIsLoading: LiveData<Boolean>
        get() = _dataIsLoading

    private val _mealDetail = MutableLiveData<MealDetail>()
    val mealDetail: LiveData<MealDetail>
        get() = _mealDetail

    init {
        getMealDetail(meal.id)
    }

    private fun getMealDetail(id: Long) {
        _dataIsLoading.value = true

        viewModelScope.launch {
            when (val response = mealsRepository.getMeal(id)) {
                is DataResponse.Success -> {
                    _mealDetail.value = response.value
                    _dataIsLoading.value = false
                }
                is DataResponse.GenericError ->  {
                    // Handle error
                    _dataIsLoading.value = false
                }
                is DataResponse.ConnectionError ->  {
                    // Handle error
                    _dataIsLoading.value = false
                }
            }
        }
    }
}
