package com.example.shoppingcart.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.databinding.ItemFavoriteBinding

class FavoriteProductsAdapter(val callback: FavoriteAdapterCallback) :
    ListAdapter<Product, FavoriteProductsAdapter.FavoritesProductsViewHolder>(
        CategoryDiffCallback()
    ) {

    inner class FavoritesProductsViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = "₹${product.price.toString()}"
            binding.ivProduct.load(product.icon)
            binding.ivFavoriteFilled.setOnClickListener {
                callback.removeFromFavorite(product.copy(isFavorite = false))
            }
            binding.btnAdd.setOnClickListener {
                callback.addProductToCart(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesProductsViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}

interface FavoriteAdapterCallback {
    fun removeFromFavorite(product: Product)
    fun addProductToCart(product: Product)
}
