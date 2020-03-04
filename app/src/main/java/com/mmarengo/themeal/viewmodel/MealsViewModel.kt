package com.mmarengo.themeal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mmarengo.themeal.R
import com.mmarengo.themeal.model.Meal
import com.mmarengo.themeal.repository.DataResponse
import com.mmarengo.themeal.repository.MealsRepository
import com.mmarengo.themeal.repository.ResponseCallback

class MealsViewModel : ViewModel() {

    private val mealRepository: MealsRepository by lazy { MealsRepository() }

    private var _currentSearch: String? = null
    val currentSearch: String?
        get() = _currentSearch

    private val _dataMeals = MutableLiveData<List<Meal>>()
    val dataMeals: LiveData<List<Meal>>
        get() = _dataMeals

    private val _dataIsLoading = MutableLiveData<Boolean>()
    val dataIsLoading: LiveData<Boolean>
        get() = _dataIsLoading

    private val _eventError = MutableLiveData<Int>()
    val eventError: LiveData<Int>
        get() = _eventError

    private val _eventMealTapped = MutableLiveData<Meal?>()
    val eventMealTapped: LiveData<Meal?>
        get() = _eventMealTapped

    init {
        // Set initial image.
    }

    fun searchMeals(query: String) {
        _dataIsLoading.value = true
        mealRepository.searchMeals(query, searchResponseCallback)
        _currentSearch = query
    }

    fun onMealTapped(meal: Meal) {
        _eventMealTapped.value = meal
    }

    fun onMealTappedFinished() {
        _eventMealTapped.value = null
    }

    private val searchResponseCallback = object : ResponseCallback<List<Meal>> {

        override fun onSuccess(value: List<Meal>?) {
            _dataMeals.value = value
            _dataIsLoading.value = false
        }

        override fun onGenericError(dataResponse: DataResponse.GenericError) {
            onError()
            // Track generic error
        }

        override fun onConnectionError(dataResponse: DataResponse.ConnectionError) {
            onError()
            // Track connection error
        }

        private fun onError() {
            _eventError.value = R.string.no_meals_error
            _dataIsLoading.value = false
        }
    }
}
