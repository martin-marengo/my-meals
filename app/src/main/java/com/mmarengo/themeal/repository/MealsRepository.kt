package com.mmarengo.themeal.repository

import com.mmarengo.themeal.dto.MealsSearchDto
import com.mmarengo.themeal.model.Meal
import com.mmarengo.themeal.model.MealDetail
import com.mmarengo.themeal.networking.MealsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MealsRepository {

    private val mealsApiService by lazy { MealsApi.retrofitService }

    private var currentSearchCall: Call<MealsSearchDto>? = null

    fun searchMeals(query: String, callback: ResponseCallback<List<Meal>>) {
        currentSearchCall?.cancel()
        currentSearchCall = mealsApiService.searchMeals(query)

        currentSearchCall?.enqueue(object : Callback<MealsSearchDto> {
            override fun onResponse(call: Call<MealsSearchDto>, response: Response<MealsSearchDto>) {
                if (response.hasSuccessBody) {
                    callback.onSuccess(response.body()!!.meals)
                } else {
                    callback.onGenericError(
                        DataResponse.GenericError(response.code(), response.errorBody())
                    )
                }
            }
            override fun onFailure(call: Call<MealsSearchDto>, t: Throwable) {
                if (!call.isCanceled) {
                    if (t is IOException) {
                        callback.onConnectionError(DataResponse.ConnectionError(t))
                    } else {
                        callback.onGenericError(DataResponse.GenericError(t = t))
                    }
                }
            }
        })
    }

    suspend fun getMeal(id: Long): DataResponse<MealDetail?> {
        return try {
            val response = mealsApiService.getMeal(id)
            if (response.hasSuccessBody) {
                DataResponse.Success(response.body()!!.mealDetail)
            } else {
                DataResponse.GenericError(response.code(), response.errorBody())
            }
        } catch (e: Exception) {
            if (e is IOException) {
                DataResponse.ConnectionError(t = e)
            } else {
                DataResponse.GenericError(t = e)
            }
        }
    }

    private val <T> Response<T>.hasSuccessBody: Boolean
        get() = isSuccessful && body() != null
}
