package com.sirmarty.drinkcrafter.di

import com.sirmarty.drinkcrafter.categories.data.CategoriesDataRepository
import com.sirmarty.drinkcrafter.categories.data.GetCategoryListService
import com.sirmarty.drinkcrafter.categories.domain.repository.CategoriesRepository
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

    @Singleton
    @Provides
    fun provideGetCategoriesListService(retrofit: Retrofit): GetCategoryListService {
        return retrofit.create(GetCategoryListService::class.java)
    }

    @Provides
    fun provideCategoriesRepository(getCategoryListService: GetCategoryListService): CategoriesRepository {
        return CategoriesDataRepository(getCategoryListService)
    }
}