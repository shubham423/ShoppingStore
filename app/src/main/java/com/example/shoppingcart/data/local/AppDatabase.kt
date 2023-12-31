package com.example.shoppingcart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppingcart.data.local.dao.ProductsDao
import com.example.shoppingcart.data.local.dao.CartProductsDao
import com.example.shoppingcart.data.local.entities.CartProductEntity
import com.example.shoppingcart.data.local.entities.ProductEntity

@Database(entities = [ProductEntity::class, CartProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): CartProductsDao
    abstract fun favoriteProductDao(): ProductsDao
}
