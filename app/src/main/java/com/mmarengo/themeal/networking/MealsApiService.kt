package com.mmarengo.themeal.networking

import com.mmarengo.themeal.dto.MealDetailDto
import com.mmarengo.themeal.dto.MealsSearchDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL: String = "https://www.themealdb.com/api/json/v1/1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface MealsApiService {

    @GET("search.php")
    fun searchMeals(@Query("s") query: String): Call<MealsSearchDto>

    @GET("lookup.php")
    suspend fun getMeal(@Query("i") id: Long): Response<MealDetailDto>
}

object MealsApi {
    val retrofitService: MealsApiService by lazy {
        retrofit.create(MealsApiService::class.java)
    }
}
