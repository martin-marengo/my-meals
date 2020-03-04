package com.mmarengo.themeal.model

import com.squareup.moshi.Json
import java.io.Serializable

data class MealDetail(
    @Json(name = "idMeal") val id: Long,
    @Json(name = "strMeal") val name: String,
    @Json(name = "strCategory") val category: String,
    @Json(name = "strMealThumb") val thumbUrl: String?,
    @Json(name = "strInstructions") val instructions: String
) : Serializable
