package com.sirmarty.drinkcrafter.drink.domain.usecase

import com.sirmarty.drinkcrafter.drink.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.drink.domain.repository.DrinkRepository
import javax.inject.Inject

class GetDrinkDetailUseCase @Inject constructor(private val repository: DrinkRepository) {
    suspend fun execute(id: Int): DrinkDetail {
        return repository.getDrinkDetail(id)
    }
}