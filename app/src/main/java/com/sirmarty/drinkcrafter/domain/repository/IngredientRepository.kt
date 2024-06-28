package com.sirmarty.drinkcrafter.domain.repository

import com.sirmarty.drinkcrafter.domain.entity.IngredientDetail

interface IngredientRepository {
    suspend fun getIngredientDetail(name: String): IngredientDetail
}