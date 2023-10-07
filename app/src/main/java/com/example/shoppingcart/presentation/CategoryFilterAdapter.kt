package com.example.shoppingcart.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.data.models.Category
import com.example.shoppingcart.databinding.ItemCategoryBinding
import com.example.shoppingcart.databinding.ItemCategoryFilterBinding
import com.example.shoppingcart.presentation.home.ProductAdapter


class CategoryFilterAdapter :
    ListAdapter<Category, CategoryFilterAdapter.CategoryFilterViewHolder>(CategoryDiffCallback()) {

    inner class CategoryFilterViewHolder(private val binding: ItemCategoryFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvCategory.text = category.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryFilterViewHolder {
        val binding =
            ItemCategoryFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryFilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CategoryFilterDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}
