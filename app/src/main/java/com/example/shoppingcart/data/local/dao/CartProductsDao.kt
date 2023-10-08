package com.example.shoppingcart.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppingcart.data.local.entities.CartProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(cartProductEntity: CartProductEntity)

    @Delete
    suspend fun deleteProduct(cartProductEntity: CartProductEntity)

    @Query("SELECT * FROM cart_products")
    fun getAllProducts(): Flow<List<CartProductEntity>>
}