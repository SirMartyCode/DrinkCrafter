package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import javax.inject.Inject

class SaveDrinkDetailUseCase @Inject constructor(private val drinkRepository: DrinkRepository) {
    suspend fun execute(drinkId: Int) {
        val drinkDetail = drinkRepository.getDrinkDetail(drinkId)
        drinkRepository.saveDrinkDetail(drinkDetail)
    }
}