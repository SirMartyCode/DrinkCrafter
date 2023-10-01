package com.sirmarty.drinkcrafter.core.drink.domain.usecase

import com.sirmarty.drinkcrafter.core.drink.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.core.drink.domain.repository.DrinkRepository
import javax.inject.Inject

class GetDrinkDetailUseCase @Inject constructor(private val repository: DrinkRepository) {
    suspend fun execute(id: Int): DrinkDetail {
        return repository.getDrinkDetail(id)
    }
}