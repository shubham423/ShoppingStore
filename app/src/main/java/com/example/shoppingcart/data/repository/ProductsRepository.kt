package com.example.shoppingcart.data.repository

import com.example.shoppingcart.data.local.entities.CartProductEntity
import com.example.shoppingcart.data.local.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun insertProducts(product: List<ProductEntity>)

    suspend fun updateProduct(product: ProductEntity)
    suspend fun removeProduct(product: ProductEntity)
    fun getAllProducts(): Flow<List<ProductEntity>>
    fun getProductsByCategoryId(categoryId: Int): Flow<List<ProductEntity>>
    fun getFavoriteProducts(): Flow<List<ProductEntity>>
}