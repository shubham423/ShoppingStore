package com.example.shoppingcart.data.repository

import java.util.concurrent.Flow

interface SyncDatastoreRepository {
    suspend fun isProductSynced(): Boolean
    suspend fun syncProduct(isSynced:Boolean)
}