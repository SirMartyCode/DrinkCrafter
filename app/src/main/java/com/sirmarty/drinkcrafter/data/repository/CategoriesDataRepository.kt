package com.sirmarty.drinkcrafter.data.repository

import com.sirmarty.drinkcrafter.data.response.toDomain
import com.sirmarty.drinkcrafter.data.service.GetCategoryListService
import com.sirmarty.drinkcrafter.domain.entity.Category
import com.sirmarty.drinkcrafter.domain.repository.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoriesDataRepository @Inject constructor(private val service: GetCategoryListService):
    CategoriesRepository {
    override suspend fun getCategoryList(): List<Category> {
        return withContext(Dispatchers.IO) {
            val response = service.getCategoryList()
            if (response.isSuccessful) {
                val result = response.body()
                result?.toDomain() ?: throw Exception("GENERIC ERROR")
            } else {
                throw Exception()
            }
        }
    }
}