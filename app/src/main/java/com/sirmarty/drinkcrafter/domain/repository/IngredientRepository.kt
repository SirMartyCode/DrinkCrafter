package com.sirmarty.drinkcrafter.domain.repository

import com.sirmarty.drinkcrafter.domain.entity.IngredientDetail
import com.sirmarty.drinkcrafter.domain.entity.IngredientName

interface IngredientRepository {
    suspend fun getIngredientDetail(name: String): IngredientDetail
    suspend fun getIngredientList(): List<IngredientName>
}