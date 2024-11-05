package com.sirmarty.drinkcrafter.domain.repository

import com.sirmarty.drinkcrafter.domain.entity.Category

interface CategoriesRepository {
    suspend fun getCategoryList(): List<Category>
}