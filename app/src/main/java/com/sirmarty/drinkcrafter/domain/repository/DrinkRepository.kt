package com.sirmarty.drinkcrafter.domain.repository

import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import kotlinx.coroutines.flow.Flow

interface DrinkRepository {
    suspend fun getDrinkDetail(id: Int): DrinkDetail
    suspend fun getSavedDrinkDetail(id: Int): DrinkDetail
    suspend fun getDrinkList(categoryName: String): List<Drink>
    suspend fun saveDrinkDetail(drinkDetail: DrinkDetail)
    suspend fun deleteDrinkDetail(drinkId: Int)
    fun isSaved(id: Int): Flow<Boolean>
    fun getAllSaved(): Flow<List<Drink>>
}