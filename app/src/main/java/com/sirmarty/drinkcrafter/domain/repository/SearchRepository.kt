package com.sirmarty.drinkcrafter.domain.repository

import com.sirmarty.drinkcrafter.domain.entity.Drink

interface SearchRepository {
    suspend fun searchDrinkByName(text: String): List<Drink>
}