package com.example.shoppingcart.di

import android.content.Context
import androidx.room.Room
import com.example.shoppingcart.data.local.AppDatabase
import com.example.shoppingcart.data.local.dao.FavoriteProductDao
import com.example.shoppingcart.data.local.dao.ProductDao
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
            "app_database"
        ).build()
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideFavoriteProductDao(database: AppDatabase): FavoriteProductDao {
        return database.favoriteProductDao()
    }
}
