package com.mmarengo.myrecipe.view.meals

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmarengo.myrecipe.R
import com.mmarengo.myrecipe.databinding.ItemMealBinding
import com.mmarengo.myrecipe.model.Meal

class MealAdapter(private val clickListener: (Meal) -> Unit) :
    ListAdapter<Meal, MealAdapter.MealViewHolder>(MealDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMealBinding.inflate(layoutInflater, parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class MealViewHolder(private val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal, clickListener: (Meal) -> Unit) {
            binding.meal = meal
            itemView.setOnClickListener { clickListener(meal) }

            if (meal.thumbUrl == null) {
                binding.mealImage.setImageResource(R.drawable.ic_broken_image)
            } else {
                val uri = Uri.parse(meal.thumbUrl)
                Glide.with(binding.mealImage.context)
                    .load(uri)
                    .error(R.drawable.ic_broken_image)
                    .placeholder(R.drawable.loading_animation)
                    .into(binding.mealImage)
            }
        }
    }
}
