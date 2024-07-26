package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import javax.inject.Inject

class GetDrinkListByCategoryUseCase @Inject constructor(private val repository: DrinkRepository) {
    suspend fun execute(categoryName: String): List<Drink> {
        return repository.getDrinkListByCategory(categoryName)
    }
}