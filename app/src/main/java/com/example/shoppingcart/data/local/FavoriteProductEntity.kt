package com.example.shoppingcart.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProductEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val icon: String,
    val name: String,
    val price: Double
)
