package com.mmarengo.myrecipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mmarengo.myrecipe.R
import com.mmarengo.myrecipe.model.Meal
import com.mmarengo.myrecipe.repository.ErrorType
import com.mmarengo.myrecipe.repository.MealsRepository
import com.mmarengo.myrecipe.repository.ResponseCallback

class MealsViewModel : ViewModel() {

    private val mealRepository = MealsRepository()

    private val _dataMeals = MutableLiveData<List<Meal>>()
    val dataMeals: LiveData<List<Meal>>
        get() = _dataMeals

    private val _dataIsLoading = MutableLiveData<Boolean>()
    val dataIsLoading: LiveData<Boolean>
        get() = _dataIsLoading

    private val _eventError = MutableLiveData<Int>()
    val eventError: LiveData<Int>
        get() = _eventError

    init {
        // set initial image for no meals
    }

    fun searchMeals(query: String) {
        _dataIsLoading.value = true
        mealRepository.searchMeal(query, searchResponseCallback)
    }

    private val searchResponseCallback = object :
        ResponseCallback<List<Meal>> {

        override fun onSuccess(value: List<Meal>?) {
            _dataMeals.value = value
            _dataIsLoading.value = false
        }

        override fun onError(errorType: ErrorType, errorCode: Int?, t: Throwable?) {
            _eventError.value = R.string.no_meals_error
            _dataIsLoading.value = false
        }
    }
}
