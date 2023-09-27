package com.sirmarty.drinkcrafter.core.categories.data

import com.sirmarty.drinkcrafter.core.categories.domain.entity.Category
import com.sirmarty.drinkcrafter.core.categories.domain.repository.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoriesDataRepository @Inject constructor(private val service: GetCategoryListService):
    CategoriesRepository {
    override suspend fun getCategoryList(): List<Category> {
        return withContext(Dispatchers.IO) {
            val response = service.getCategoryList()
            response.body()?.categories ?: emptyList()
        }
    }
}