package com.example.shoppingcart.data.repository

import android.content.Context
import com.example.shoppingcart.data.local.FavoriteProductEntity
import com.example.shoppingcart.data.local.ProductEntity
import com.example.shoppingcart.data.local.dao.FavoriteProductDao
import com.example.shoppingcart.data.local.dao.ProductDao
import com.example.shoppingcart.data.models.ProductsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

class ProductRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val productDao: ProductDao,
    private val favoriteProductDao: FavoriteProductDao
) {
    suspend fun insertProduct(product: ProductEntity) {
        productDao.insertProduct(product)
    }

    suspend fun removeProductFromCart(product: ProductEntity) {
        productDao.deleteProduct(product)
    }

    suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProductEntity) {
        favoriteProductDao.insertFavoriteProduct(favoriteProduct)
    }

    suspend fun removeFavoriteProduct(favoriteProduct: FavoriteProductEntity) {
        favoriteProductDao.deleteFavoriteProduct(favoriteProduct)
    }

    fun getAllCartProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    fun getAllFavoriteProducts(): Flow<List<FavoriteProductEntity>> {
        return favoriteProductDao.getAllFavoriteProducts()
    }

    fun getProductsResponse(): ProductsResponse? {
        val jsonFileString = getJsonDataFromAsset(context, "shopping.json")
        val type = object : TypeToken<ProductsResponse>() {}.type
        return Gson().fromJson(jsonFileString, type)
    }

    private fun getJsonDataFromAsset(
        context: Context, fileName: String
    ): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
        } catch (exp: IOException) {
            exp.printStackTrace()
            return null
        }
        return jsonString
    }
}
