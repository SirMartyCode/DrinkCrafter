package com.sirmarty.drinkcrafter.core.categories.domain.usecase

import com.sirmarty.drinkcrafter.core.categories.domain.entity.Category
import com.sirmarty.drinkcrafter.core.categories.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(private val repository: CategoriesRepository) {
    suspend fun execute(): List<Category> {
        return repository.getCategoryList()
    }
}