package com.mmarengo.themeal.networking

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.mmarengo.themeal.dto.MealDetailDto
import com.mmarengo.themeal.dto.MealsSearchDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL: String = "https://www.themealdb.com/api/json/v1/1/"

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BASIC
}

private val okHttpClient = OkHttpClient.Builder()
    .addNetworkInterceptor(StethoInterceptor())
    .addInterceptor(loggingInterceptor)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface MealsApiService {

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): Response<MealsSearchDto>

    @GET("lookup.php")
    suspend fun getMeal(@Query("i") id: Long): Response<MealDetailDto>
}

object MealsApi {
    val retrofitService: MealsApiService by lazy {
        retrofit.create(MealsApiService::class.java)
    }
}
