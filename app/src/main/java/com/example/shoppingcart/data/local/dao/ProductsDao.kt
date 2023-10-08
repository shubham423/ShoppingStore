package com.example.shoppingcart.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppingcart.data.local.CartProductEntity
import com.example.shoppingcart.data.local.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteProduct(productEntity: ProductEntity)

    @Delete
    suspend fun deleteFavoriteProduct(favoriteProduct: CartProductEntity)

    @Query("SELECT * FROM products")
    fun getAllFavoriteProducts(): Flow<List<CartProductEntity>>

    @Query("SELECT COUNT(*) FROM products WHERE id = :productId")
     fun isProductInFavorites(productId: Int): Int
}