package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.IngredientDetail
import com.sirmarty.drinkcrafter.domain.repository.IngredientRepository
import javax.inject.Inject

class GetIngredientDetailUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    suspend fun execute(name: String): IngredientDetail {
        return ingredientRepository.getIngredientDetail(name)
    }
}