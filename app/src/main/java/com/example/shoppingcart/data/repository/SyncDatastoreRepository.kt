package com.example.shoppingcart.data.repository

interface SyncDatastoreRepository {
    suspend fun isProductSynced(): Boolean
    suspend fun syncProduct(isSynced:Boolean)
}