package com.example.shoppingcart.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.shoppingcart.data.local.dao.CartProductsDao
import com.example.shoppingcart.data.local.dao.ProductsDao
import com.example.shoppingcart.data.repository.CartRepository
import com.example.shoppingcart.data.repository.CartRepositoryImpl
import com.example.shoppingcart.data.repository.ProductRepositoryImpl
import com.example.shoppingcart.data.repository.ProductsRepository
import com.example.shoppingcart.data.repository.SyncDatastoreRepository
import com.example.shoppingcart.data.repository.SyncDatastoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSyncDataStoreRepository(dataStore: DataStore<Preferences>): SyncDatastoreRepository =
        SyncDatastoreRepositoryImpl(dataStore)

    @Provides
    @Singleton
    fun provideProductRepository(
        @ApplicationContext context: Context,
        productsDao: ProductsDao
    ): ProductsRepository =
        ProductRepositoryImpl(context, productsDao)


    @Provides
    @Singleton
    fun provideCartRepository(
        cartProductsDao: CartProductsDao
    ): CartRepository =
        CartRepositoryImpl(cartProductsDao)
}