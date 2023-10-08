package com.example.shoppingcart.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.data.models.Category
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.databinding.ItemCategoryBinding
import com.example.shoppingcart.presentation.home.ProductAdapter
import com.example.shoppingcart.util.gone
import com.example.shoppingcart.util.setSafeOnClickListener
import com.example.shoppingcart.util.visible


class CategoryAdapter(val onFavoriteClicked: (product: Product) -> Unit) :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvTitle.text = category.name
            val productAdapter = ProductAdapter(onFavoriteClicked = {product->
                onFavoriteClicked.invoke(product)
            })
            productAdapter.submitList(category.items)
            binding.productRecyclerView.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.productRecyclerView.adapter = productAdapter
            binding.ivArrow.setOnClickListener {
                if ( binding.productRecyclerView.isVisible){
                    binding.productRecyclerView.gone()
                }else{
                    binding.productRecyclerView.visible()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}
