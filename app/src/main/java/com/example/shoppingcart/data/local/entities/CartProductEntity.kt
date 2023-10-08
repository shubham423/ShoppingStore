package com.example.shoppingcart.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_products")
data class CartProductEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val icon: String,
    val name: String,
    val price: Double,
    val quantity:Int=1
)
