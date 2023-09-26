package com.sirmarty.drinkcrafter.drink.domain.repository

import com.sirmarty.drinkcrafter.drink.domain.entity.Drink
import com.sirmarty.drinkcrafter.drink.domain.entity.DrinkDetail

interface DrinkRepository {
    suspend fun getDrinkDetail(id: Int): DrinkDetail
    suspend fun getDrinkList(categoryName: String): List<Drink>
}