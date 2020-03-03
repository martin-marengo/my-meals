package com.mmarengo.myrecipe.model

import com.squareup.moshi.Json
import java.io.Serializable

data class Meal(
    @Json(name = "idMeal") val id: Long,
    @Json(name = "strMeal") val name: String,
    @Json(name = "strCategory") val category: String,
    @Json(name = "strMealThumb") val thumbUrl: String?
) : Serializable
