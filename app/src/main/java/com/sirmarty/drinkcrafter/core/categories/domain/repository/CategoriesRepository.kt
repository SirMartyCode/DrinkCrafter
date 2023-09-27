package com.sirmarty.drinkcrafter.core.categories.domain.repository

import com.sirmarty.drinkcrafter.core.categories.domain.entity.Category

interface CategoriesRepository {
    suspend fun getCategoryList(): List<Category>
}