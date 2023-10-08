package com.example.shoppingcart.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shoppingcart.R
import com.example.shoppingcart.data.models.Product
import com.example.shoppingcart.databinding.ItemProductBinding
import com.example.shoppingcart.util.toFavoriteProductEntity

class ProductAdapter(val viewModel: HomeViewModel, val fadeOutAnimation: Animation) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvPrice.text = "$${product.price}"
            binding.ivProduct.load(product.icon)
            if (viewModel.isProductInFavorite(product.toFavoriteProductEntity())) {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
            }
            binding.ivFavorite.setOnClickListener {
                if (viewModel.isProductInFavorite(product.toFavoriteProductEntity())) {
                    viewModel.removeFavoriteProduct(product.toFavoriteProductEntity())
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                    binding.ivFavorite.startAnimation(fadeOutAnimation)
                } else {
                    viewModel.addFavoriteProduct(product.toFavoriteProductEntity())
                    binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
                    binding.ivFavorite.startAnimation(fadeOutAnimation)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}

