package com.example.shoppingcart.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.databinding.ItemCartProductBinding

class CartProductsAdapter(val removeFromFavorite: (product: Product) -> Unit) :
    ListAdapter<Product, CartProductsAdapter.CartProductsViewHolder>(
        CartDiffCallback()
    ) {

    inner class CartProductsViewHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = "₹${product.price.toString()}"
            binding.ivProduct.load(product.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductsViewHolder {
        val binding =
            ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CartDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
