package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import javax.inject.Inject

class DeleteDrinkDetailUseCase @Inject constructor(private val drinkRepository: DrinkRepository) {
    suspend fun execute(drinkId: Int) {
        drinkRepository.deleteDrinkDetail(drinkId)
    }
}