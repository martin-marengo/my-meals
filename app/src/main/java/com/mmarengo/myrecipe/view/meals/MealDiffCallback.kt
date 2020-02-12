package com.mmarengo.myrecipe.view.meals

import androidx.recyclerview.widget.DiffUtil
import com.mmarengo.myrecipe.model.Meal

class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {

    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.name == newItem.name &&
                oldItem.category == newItem.category &&
                oldItem.thumbUrl == newItem.thumbUrl
    }
}
