package com.example.shoppingcart.data.repository

import android.content.Context
import com.example.shoppingcart.data.local.entities.CartProductEntity
import com.example.shoppingcart.data.local.entities.ProductEntity
import com.example.shoppingcart.data.models.ProductsResponse
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun insertProducts(product: List<ProductEntity>)

    suspend fun updateProduct(product: ProductEntity)
    suspend fun removeProduct(product: ProductEntity)
    fun getAllProducts(): Flow<List<ProductEntity>>
    fun getProductsByCategoryId(categoryId: Int): Flow<List<ProductEntity>>
    fun getFavoriteProducts(): Flow<List<ProductEntity>>

    fun getProductsResponse(): ProductsResponse?

    fun getJsonDataFromAsset(
        context: Context, fileName: String
    ): String?
}