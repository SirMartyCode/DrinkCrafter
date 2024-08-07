package com.sirmarty.drinkcrafter.di

import com.sirmarty.drinkcrafter.data.dao.DrinkDetailDao
import com.sirmarty.drinkcrafter.data.repository.CategoriesDataRepository
import com.sirmarty.drinkcrafter.data.repository.DrinkDataRepository
import com.sirmarty.drinkcrafter.data.repository.IngredientDataRepository
import com.sirmarty.drinkcrafter.data.repository.SearchDataRepository
import com.sirmarty.drinkcrafter.data.service.GetCategoryListService
import com.sirmarty.drinkcrafter.data.service.GetDrinkDetailService
import com.sirmarty.drinkcrafter.data.service.GetDrinkListByIngredientService
import com.sirmarty.drinkcrafter.data.service.GetDrinkListService
import com.sirmarty.drinkcrafter.data.service.GetIngredientDetailService
import com.sirmarty.drinkcrafter.data.service.GetIngredientListService
import com.sirmarty.drinkcrafter.data.service.GetRandomDrinkDetailService
import com.sirmarty.drinkcrafter.data.service.SearchDrinkByNameService
import com.sirmarty.drinkcrafter.domain.repository.CategoriesRepository
import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import com.sirmarty.drinkcrafter.domain.repository.IngredientRepository
import com.sirmarty.drinkcrafter.domain.repository.SearchRepository
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

    @Singleton
    @Provides
    fun provideGetDrinkListByIngredientService(retrofit: Retrofit): GetDrinkListByIngredientService {
        return retrofit.create(GetDrinkListByIngredientService::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchDrinkByNameService(retrofit: Retrofit): SearchDrinkByNameService {
        return retrofit.create(SearchDrinkByNameService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetRandomDrinkDetailService(retrofit: Retrofit): GetRandomDrinkDetailService {
        return retrofit.create(GetRandomDrinkDetailService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetIngredientDetailService(retrofit: Retrofit): GetIngredientDetailService {
        return retrofit.create(GetIngredientDetailService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetIngredientListService(retrofit: Retrofit): GetIngredientListService {
        return retrofit.create(GetIngredientListService::class.java)
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
        getRandomDrinkDetailService: GetRandomDrinkDetailService,
        getDrinkListService: GetDrinkListService,
        getDrinkListByIngredientService: GetDrinkListByIngredientService,
        drinkDetailDao: DrinkDetailDao
    ): DrinkRepository {
        return DrinkDataRepository(
            getDrinkDetailService,
            getRandomDrinkDetailService,
            getDrinkListService,
            getDrinkListByIngredientService,
            drinkDetailDao
        )
    }

    @Provides
    fun provideSearchRepository(searchDrinkByNameService: SearchDrinkByNameService): SearchRepository {
        return SearchDataRepository(searchDrinkByNameService)
    }

    @Provides
    fun provideIngredientRepository(
        getIngredientDetailService: GetIngredientDetailService,
        getIngredientListService: GetIngredientListService
    ): IngredientRepository {
        return IngredientDataRepository(getIngredientDetailService, getIngredientListService)
    }

    // endregion
}