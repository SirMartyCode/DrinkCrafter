package com.sirmarty.drinkcrafter.categories.domain.usecase

import com.sirmarty.drinkcrafter.categories.domain.entity.Category
import com.sirmarty.drinkcrafter.categories.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(private val repository: CategoriesRepository) {
    suspend fun execute(): List<Category> {
        return repository.getCategoryList()
    }
}