package com.example.shoppingcart.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppingcart.data.local.FavoriteProductEntity
import kotlinx.coroutines.flow.StateFlow

@Dao
interface FavoriteProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProductEntity)

    @Delete
    suspend fun deleteFavoriteProduct(favoriteProduct: FavoriteProductEntity)

    @Query("SELECT * FROM favorite_products")
    fun getAllFavoriteProducts(): StateFlow<List<FavoriteProductEntity>>
}