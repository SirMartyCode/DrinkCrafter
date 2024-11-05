package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsSavedUseCase @Inject constructor(private val drinkRepository: DrinkRepository) {
    fun execute(id: Int): Flow<Boolean> {
        return drinkRepository.isSaved(id)
    }
}