package com.example.shoppingcart.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.databinding.ItemCartProductBinding

class CartProductsAdapter(val callback: CartAdapterCallback) :
    ListAdapter<Product, CartProductsAdapter.CartProductsViewHolder>(
        CartDiffCallback()
    ) {

    inner class CartProductsViewHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = "₹${product.price.toString()}"
            binding.ivProduct.load(product.icon)
            binding.tvTotalPrice.text = "₹${(product.price * product.quantity).toInt()}"
            binding.tvQuantity.text = product.quantity.toString()
            binding.btnAddMinus.setOnClickListener {
                val quantity = product.quantity
                callback.decrementQuantity(product.copy(quantity = quantity - 1))
            }
            binding.btnPlus.setOnClickListener {
                val quantity = product.quantity
                callback.incrementQuantity(product.copy(quantity = quantity + 1))
            }
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

interface CartAdapterCallback {
    fun decrementQuantity(product: Product)
    fun incrementQuantity(product: Product)
}
