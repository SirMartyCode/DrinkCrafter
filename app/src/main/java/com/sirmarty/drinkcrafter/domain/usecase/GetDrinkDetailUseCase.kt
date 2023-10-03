package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import javax.inject.Inject

class GetDrinkDetailUseCase @Inject constructor(private val repository: DrinkRepository) {
    suspend fun execute(id: Int): DrinkDetail {
        return repository.getDrinkDetail(id)
    }
}