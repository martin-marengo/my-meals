package com.mmarengo.themeal.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmarengo.themeal.R
import com.mmarengo.themeal.model.Meal
import com.mmarengo.themeal.repository.DataResponse
import com.mmarengo.themeal.repository.MealsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MealsViewModel : ViewModel() {

    companion object {
        const val TYPING_SEARCH_DELAY: Long = 500
    }
    private val mealsRepository: MealsRepository by lazy { MealsRepository() }

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

    private var searchTimer: SearchTimer? = null
    private var currentSearchJob: Job? = null

    fun searchMeals(query: String) {
        searchTimer?.cancel()
        if (searchTimer == null) {
            searchTimer = SearchTimer(TYPING_SEARCH_DELAY)
        }
        searchTimer?.let {
            it.query = query
            it.start()
        }
    }

    fun onMealTapped(meal: Meal) {
        _eventMealTapped.value = meal
    }

    fun onMealTappedFinished() {
        _eventMealTapped.value = null
    }

    private inner class SearchTimer(time: Long) : CountDownTimer(time, time) {
        var query: String = ""

        override fun onTick(millisUntilFinished: Long) {
            // no-op
        }
        override fun onFinish() {
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        if (_dataIsLoading.value != true) {
            _dataIsLoading.value = true
        }
        currentSearchJob?.cancel()
        currentSearchJob = viewModelScope.launch {
            when (val response = mealsRepository.searchMeals(query)) {
                is DataResponse.Success -> {
                    _dataMeals.value = response.value
                    _dataIsLoading.value = false
                }
                is DataResponse.GenericError ->  {
                    // Track generic error
                    onError()
                }
                is DataResponse.ConnectionError ->  {
                    // Track connection error
                    onError()
                }
            }
        }
        _currentSearch = query
    }

    private fun onError() {
        _eventError.value = R.string.no_meals_error
        _dataIsLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        searchTimer?.cancel()
        searchTimer = null
    }
}
