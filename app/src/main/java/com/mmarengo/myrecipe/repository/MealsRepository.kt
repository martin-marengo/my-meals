package com.mmarengo.myrecipe.repository

import com.mmarengo.myrecipe.dto.MealsSearchDto
import com.mmarengo.myrecipe.model.Meal
import com.mmarengo.productskt.networking.MealsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MealsRepository {

    private val mealsApiService by lazy { MealsApi.retrofitService }

    private var currentSearchCall: Call<MealsSearchDto>? = null

    fun searchMeal(query: String, callback: ResponseCallback<List<Meal>>) {
        currentSearchCall?.cancel()
        currentSearchCall = mealsApiService.searchMeals(query)
        currentSearchCall?.enqueue(object : Callback<MealsSearchDto> {

            override fun onResponse(call: Call<MealsSearchDto>, response: Response<MealsSearchDto>) {
                if (response.isSuccessful && response.body() != null) {
                    callback.onSuccess(response.body()!!.meals)
                } else {
                    callback.onError(ErrorType.GENERIC, response.code())
                }
            }

            override fun onFailure(call: Call<MealsSearchDto>, t: Throwable) {
                if (!call.isCanceled) {
                    if (t is IOException) {
                        callback.onError(ErrorType.CONNECTION, t = t)
                    } else {
                        callback.onError(ErrorType.GENERIC, t = t)
                    }
                }
            }
        })
    }
}
