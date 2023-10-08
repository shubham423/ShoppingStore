package com.example.shoppingcart.data.repository

import android.content.Context
import com.example.shoppingcart.data.local.entities.CartProductEntity
import com.example.shoppingcart.data.local.entities.ProductEntity
import com.example.shoppingcart.data.local.dao.ProductsDao
import com.example.shoppingcart.data.local.dao.CartProductsDao
import com.example.shoppingcart.data.models.ProductsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val productDao: ProductsDao,
) : ProductsRepository {
    override suspend fun insertProducts(product: List<ProductEntity>) {
        productDao.insertAll(product)
    }

    override suspend fun updateProduct(product: ProductEntity) {
        productDao.updateProduct(product)
    }

    override suspend fun removeProduct(product: ProductEntity) {
        productDao.deleteProduct(product)
    }

    override fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }


    override fun getProductsByCategoryId(categoryId: Int): Flow<List<ProductEntity>> {
        return productDao.getProductsByCategoryId(categoryId)
    }

    override fun getFavoriteProducts(): Flow<List<ProductEntity>> {
        return productDao.getFavoriteProducts()
    }

    override fun getProductsResponse(): ProductsResponse? {
        val jsonFileString = getJsonDataFromAsset(context, "shopping.json")
        val type = object : TypeToken<ProductsResponse>() {}.type
        return Gson().fromJson(jsonFileString, type)
    }

    override fun getJsonDataFromAsset(
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
