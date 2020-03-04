package com.mmarengo.themeal.dto

import com.mmarengo.themeal.model.Meal
import com.squareup.moshi.Json

data class MealsSearchDto(
    @Json(name = "meals") val meals: List<Meal>?
)
