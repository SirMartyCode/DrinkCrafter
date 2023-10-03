package com.sirmarty.drinkcrafter.core.search.domain.repository

import com.sirmarty.drinkcrafter.core.drink.domain.entity.Drink

interface SearchRepository {
    suspend fun searchDrinkByName(text: String): List<Drink>
}