package com.example.shoppingcart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppingcart.data.local.dao.FavoriteProductDao
import com.example.shoppingcart.data.local.dao.ProductDao

@Database(entities = [ProductEntity::class, FavoriteProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun favoriteProductDao(): FavoriteProductDao
}
