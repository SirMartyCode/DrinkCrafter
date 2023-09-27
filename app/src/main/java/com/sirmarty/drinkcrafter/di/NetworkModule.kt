package com.sirmarty.drinkcrafter.di

import com.sirmarty.drinkcrafter.core.categories.data.CategoriesDataRepository
import com.sirmarty.drinkcrafter.core.categories.data.GetCategoryListService
import com.sirmarty.drinkcrafter.core.categories.domain.repository.CategoriesRepository
import com.sirmarty.drinkcrafter.core.drink.data.DrinkDataRepository
import com.sirmarty.drinkcrafter.core.drink.data.GetDrinkDetailService
import com.sirmarty.drinkcrafter.core.drink.data.GetDrinkListService
import com.sirmarty.drinkcrafter.core.drink.domain.repository.DrinkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //==============================================================================================
    // region Services

    @Singleton
    @Provides
    fun provideGetCategoriesListService(retrofit: Retrofit): GetCategoryListService {
        return retrofit.create(GetCategoryListService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetDrinkDetailService(retrofit: Retrofit): GetDrinkDetailService {
        return retrofit.create(GetDrinkDetailService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetDrinkListService(retrofit: Retrofit): GetDrinkListService {
        return retrofit.create(GetDrinkListService::class.java)
    }

    // endregion
    //==============================================================================================
    // region Repositories

    @Provides
    fun provideCategoriesRepository(getCategoryListService: GetCategoryListService): CategoriesRepository {
        return CategoriesDataRepository(getCategoryListService)
    }

    @Provides
    fun provideDrinkRepository(
        getDrinkDetailService: GetDrinkDetailService,
        getDrinkListService: GetDrinkListService
    ): DrinkRepository {
        return DrinkDataRepository(getDrinkDetailService, getDrinkListService)
    }

    // endregion
}