package com.example.shoppingcart.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.shoppingcart.data.local.entities.CartProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(cartProductEntity: CartProductEntity)

    @Delete
    suspend fun deleteCartProduct(cartProductEntity: CartProductEntity)

    @Query("SELECT * FROM cart_products")
    fun getAllProducts(): Flow<List<CartProductEntity>>

    @Upsert()
    suspend fun updateCartProduct(cartProductEntity: CartProductEntity)

    @Query("SELECT COUNT(*) FROM cart_products")
    fun getCartProductsCount(): Flow<Int>

}