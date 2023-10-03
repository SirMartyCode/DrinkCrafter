package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.repository.SearchRepository
import javax.inject.Inject

class SearchDrinkByNameUseCase @Inject constructor(private val searchDrinkRepository: SearchRepository) {
    suspend fun searchDrinkByName(text: String): List<Drink> {
        return searchDrinkRepository.searchDrinkByName(text)
    }
}