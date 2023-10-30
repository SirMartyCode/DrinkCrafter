package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import javax.inject.Inject

class GetDrinkListByIngredientUseCase @Inject constructor(private val repository: DrinkRepository) {
    suspend fun execute(ingredient: String): List<Drink> {
        return repository.getDrinkListByIngredient(ingredient)
    }
}