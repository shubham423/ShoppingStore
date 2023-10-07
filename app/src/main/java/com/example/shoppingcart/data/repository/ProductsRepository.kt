package com.example.shoppingcart.data.repository

import android.content.Context
import com.example.shoppingcart.data.models.ProductsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class ProductRepository @Inject constructor(@ApplicationContext val context: Context) {

    fun getProductsResponse(): ProductsResponse? {
        val jsonFileString = getJsonDataFromAsset(context, "shopping.json")
        val type = object : TypeToken<ProductsResponse>() {}.type
        return Gson().fromJson(jsonFileString, type)
    }

    private fun getJsonDataFromAsset(
        context: Context,
        fileName: String
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
