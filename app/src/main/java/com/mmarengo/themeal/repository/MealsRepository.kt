package com.mmarengo.themeal.repository

import com.mmarengo.themeal.model.Meal
import com.mmarengo.themeal.model.MealDetail
import com.mmarengo.themeal.networking.MealsApi
import kotlinx.coroutines.CancellationException
import retrofit2.Response
import java.io.IOException

class MealsRepository {

    private val mealsApiService by lazy { MealsApi.retrofitService }

    suspend fun searchMeals(query: String) : DataResponse<List<Meal>?> {
        return try {
            val response = mealsApiService.searchMeals(query)
            if (response.hasSuccessBody) {
                DataResponse.Success(response.body()!!.meals)
            } else {
                DataResponse.GenericError(response.code(), response.errorBody())
            }
        } catch (e: Exception) {
            when(e) {
                is CancellationException -> DataResponse.Cancelled
                is IOException -> DataResponse.ConnectionError(t = e)
                else -> DataResponse.GenericError(t = e)
            }
        }
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
            when(e) {
                is CancellationException -> DataResponse.Cancelled
                is IOException -> DataResponse.ConnectionError(t = e)
                else -> DataResponse.GenericError(t = e)
            }
        }
    }

    private val <T> Response<T>.hasSuccessBody: Boolean
        get() = isSuccessful && body() != null
}
