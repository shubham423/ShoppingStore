package com.example.shoppingcart.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val icon: String,
    val name: String,
    val price: Double,
    val isFavorite: Boolean = false,
    val categoryId:Int,
    val categoryName:String
)
