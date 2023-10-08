package com.example.shoppingcart.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SyncDatastoreRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    SyncDatastoreRepository {
    companion object {
        val IS_SYNCED_KEY = booleanPreferencesKey("sync_key")
    }

    override suspend fun isProductSynced(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[IS_SYNCED_KEY] ?: false
    }

    override suspend fun syncProduct(isSynced: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_SYNCED_KEY] = isSynced
        }
    }

}