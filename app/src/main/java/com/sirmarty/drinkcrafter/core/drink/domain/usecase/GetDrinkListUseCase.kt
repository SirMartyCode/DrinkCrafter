package com.sirmarty.drinkcrafter.core.drink.domain.usecase

import com.sirmarty.drinkcrafter.core.drink.domain.entity.Drink
import com.sirmarty.drinkcrafter.core.drink.domain.repository.DrinkRepository
import javax.inject.Inject

class GetDrinkListUseCase @Inject constructor(private val repository: DrinkRepository) {
    suspend fun execute(categoryName: String): List<Drink> {
        return repository.getDrinkList(categoryName)
    }
}