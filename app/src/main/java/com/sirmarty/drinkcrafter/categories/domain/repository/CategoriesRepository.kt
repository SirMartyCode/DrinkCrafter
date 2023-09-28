package com.sirmarty.drinkcrafter.categories.domain.repository

import com.sirmarty.drinkcrafter.categories.domain.entity.Category

interface CategoriesRepository {
    suspend fun getCategoryList(): List<Category>
}