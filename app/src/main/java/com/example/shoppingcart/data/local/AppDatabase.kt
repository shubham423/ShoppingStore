package com.example.shoppingcart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppingcart.data.local.dao.FavoriteProductDao
import com.example.shoppingcart.data.local.dao.CartProductsDao

@Database(entities = [ProductEntity::class, FavoriteProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): CartProductsDao
    abstract fun favoriteProductDao(): FavoriteProductDao
}
