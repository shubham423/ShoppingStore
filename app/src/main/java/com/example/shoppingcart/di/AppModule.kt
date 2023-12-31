package com.example.shoppingcart.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.shoppingcart.data.local.AppDatabase
import com.example.shoppingcart.data.local.dao.ProductsDao
import com.example.shoppingcart.data.local.dao.CartProductsDao
import com.example.shoppingcart.data.repository.CartRepository
import com.example.shoppingcart.data.repository.CartRepositoryImpl
import com.example.shoppingcart.data.repository.ProductRepositoryImpl
import com.example.shoppingcart.data.repository.ProductsRepository
import com.example.shoppingcart.data.repository.SyncDatastoreRepository
import com.example.shoppingcart.data.repository.SyncDatastoreRepositoryImpl
import com.example.shoppingcart.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): CartProductsDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteProductDao(database: AppDatabase): ProductsDao {
        return database.favoriteProductDao()
    }


}
