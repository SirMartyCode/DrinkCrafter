package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.Category
import com.sirmarty.drinkcrafter.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(private val repository: CategoriesRepository) {
    suspend fun execute(): List<Category> {
        return repository.getCategoryList()
    }
}