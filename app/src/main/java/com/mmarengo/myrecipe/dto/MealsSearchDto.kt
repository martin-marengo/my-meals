package com.mmarengo.myrecipe.dto

import com.mmarengo.myrecipe.model.Meal
import com.squareup.moshi.Json

data class MealsSearchDto(
    @Json(name = "meals") val meals: List<Meal>?
)
