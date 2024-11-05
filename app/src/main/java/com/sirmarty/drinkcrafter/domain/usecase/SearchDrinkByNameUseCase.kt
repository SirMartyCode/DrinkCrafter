package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.repository.SearchRepository
import javax.inject.Inject

class SearchDrinkByNameUseCase @Inject constructor(private val searchDrinkRepository: SearchRepository) {
    suspend fun execute(text: String): List<Drink> {
        return if (text.isNotEmpty()) {
            searchDrinkRepository.searchDrinkByName(text)
        } else {
            emptyList()
        }
    }
}