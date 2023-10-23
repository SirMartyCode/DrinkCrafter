package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedDrinksListUseCase @Inject constructor(private val drinkRepository: DrinkRepository) {
    fun execute(): Flow<List<Drink>> {
        return drinkRepository.getAllSaved()
    }
}