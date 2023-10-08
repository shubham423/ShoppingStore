package com.example.shoppingcart.data.repository

import com.example.shoppingcart.data.local.entities.CartProductEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addToCart(cartProductEntity: CartProductEntity)
    suspend fun removeFromCart(cartProductEntity: CartProductEntity)
    suspend fun updateCartProduct(cartProductEntity: CartProductEntity)
    fun getAllCartProducts(): Flow<List<CartProductEntity>>
}