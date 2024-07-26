package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.IngredientName
import com.sirmarty.drinkcrafter.domain.repository.IngredientRepository
import javax.inject.Inject

class GetIngredientListUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    suspend fun execute(): List<IngredientName> {
        return ingredientRepository.getIngredientList().sortedBy { it.name }
    }
}