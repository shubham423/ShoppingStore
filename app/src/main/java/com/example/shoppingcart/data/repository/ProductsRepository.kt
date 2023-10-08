package com.example.shoppingcart.data.repository

import android.content.Context
import com.example.shoppingcart.data.local.CartProductEntity
import com.example.shoppingcart.data.local.ProductEntity
import com.example.shoppingcart.data.local.dao.ProductsDao
import com.example.shoppingcart.data.local.dao.CartProductsDao
import com.example.shoppingcart.data.models.ProductsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

class ProductRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val productDao: ProductsDao,
    private val cartProductsDao: CartProductsDao
) {
    suspend fun insertProducts(product: List<ProductEntity>) {
        productDao.insertAll(product)
    }

    suspend fun insertProductInCart(product: CartProductEntity) {
        cartProductsDao.insertProduct(product)
    }

    suspend fun updateProduct(product: ProductEntity) {
        productDao.updateProduct(product)
    }

    suspend fun removeProductFromCart(product: CartProductEntity) {
        cartProductsDao.deleteProduct(product)
    }

    suspend fun removeProduct(product: ProductEntity) {
        productDao.deleteProduct(product)
    }

    fun getAllCartProducts(): Flow<List<CartProductEntity>> {
        return cartProductsDao.getAllProducts()
    }

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    fun isProductInFavorites(product: ProductEntity): Int {
        return productDao.isProductFavorite(product.id)
    }

    fun getProductsByCategoryId(categoryId:Int): Flow<List<ProductEntity>> {
        return productDao.getProductsByCategoryId(categoryId)
    }

    fun getFavoriteProducts(): Flow<List<ProductEntity>> {
        return productDao.getFavoriteProducts()
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
