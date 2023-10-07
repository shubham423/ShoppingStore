package com.example.shoppingcart.data.models


data class ProductsResponse(
    val categories: List<Category>,
    val error: Any,
    val message: String,
    val status: Boolean
)

data class Category(
    val id: Int,
    val items: List<Product>,
    val name: String
)

data class Product(
    val icon: String,
    val id: Int,
    val name: String,
    val price: Double
)
