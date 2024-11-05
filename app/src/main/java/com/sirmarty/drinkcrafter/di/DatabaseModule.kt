package com.sirmarty.drinkcrafter.di

import android.content.Context
import androidx.room.Room
import com.sirmarty.drinkcrafter.data.DrinkCrafterDatabase
import com.sirmarty.drinkcrafter.data.dao.DrinkDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): DrinkCrafterDatabase {
        return Room.databaseBuilder(
            appContext,
            DrinkCrafterDatabase::class.java, "drink_crafter_database"
        ).build()
    }

    //==============================================================================================
    // region Dao

    @Provides
    fun provideDrinkDetailDao(database: DrinkCrafterDatabase): DrinkDetailDao {
        return database.drinkDetailDao()
    }

    // endregion
}