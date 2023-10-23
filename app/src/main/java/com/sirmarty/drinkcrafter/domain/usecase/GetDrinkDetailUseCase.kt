package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetDrinkDetailUseCase @Inject constructor(
    private val isSavedUseCase: IsSavedUseCase,
    private val repository: DrinkRepository
) {
    suspend fun execute(id: Int): DrinkDetail {
        return if (isSavedUseCase.execute(id).first()) {
            repository.getSavedDrinkDetail(id)
        } else {
            repository.getDrinkDetail(id)
        }
    }
}