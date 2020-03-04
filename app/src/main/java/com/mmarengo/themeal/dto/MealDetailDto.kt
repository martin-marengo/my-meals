package com.mmarengo.themeal.dto

import com.mmarengo.themeal.model.MealDetail
import com.squareup.moshi.Json

data class MealDetailDto(
    @Json(name = "meals") private val meals: List<MealDetail>?
) {
    val mealDetail: MealDetail?
        get() = meals?.let { if (it.isNotEmpty()) it[0] else null }
}
