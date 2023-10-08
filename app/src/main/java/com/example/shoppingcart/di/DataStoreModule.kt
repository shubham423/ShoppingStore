package com.example.shoppingcart.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.shoppingcart.data.repository.SyncDatastoreRepository
import com.example.shoppingcart.data.repository.SyncDatastoreRepositoryImpl
import com.example.shoppingcart.util.Constants.PREFERENCES_STORE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(produceFile = {
            appContext.preferencesDataStoreFile(PREFERENCES_STORE_NAME)
        })

    @Provides
    @Singleton
    fun provideAuthTokenStore(dataStore: DataStore<Preferences>): SyncDatastoreRepository =
        SyncDatastoreRepositoryImpl(dataStore)
}