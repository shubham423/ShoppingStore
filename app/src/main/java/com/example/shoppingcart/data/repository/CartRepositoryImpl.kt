package com.example.shoppingcart.data.repository

import com.example.shoppingcart.data.local.dao.CartProductsDao
import com.example.shoppingcart.data.local.entities.CartProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val dao: CartProductsDao) : CartRepository {
    override suspend fun addToCart(cartProductEntity: CartProductEntity) {
        dao.insertCartProduct(cartProductEntity)
    }

    override suspend fun removeFromCart(cartProductEntity: CartProductEntity) {
        dao.deleteCartProduct(cartProductEntity)
    }

    override suspend fun updateCartProduct(cartProductEntity: CartProductEntity) {
      dao.updateCartProduct(cartProductEntity)
    }

    override fun getAllCartProducts(): Flow<List<CartProductEntity>> {
        TODO("Not yet implemented")
    }
}