package com.sirmarty.drinkcrafter.drink.data

import com.sirmarty.drinkcrafter.drink.domain.entity.Drink
import com.sirmarty.drinkcrafter.drink.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.drink.domain.repository.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DrinkDataRepository @Inject constructor(
    private val getDrinkDetailService: GetDrinkDetailService,
    private val getDrinkListService: GetDrinkListService
) : DrinkRepository {

    override suspend fun getDrinkDetail(id: Int): DrinkDetail {
        return withContext(Dispatchers.IO) {
            val response = getDrinkDetailService.getDrinkDetail(id)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    DrinkDetailMapper.fromJsonToEntity(result)
                } else {
                    throw Exception()
                }
            } else {
                throw Exception()
            }
        }
    }

    override suspend fun getDrinkList(categoryName: String): List<Drink> {
        return withContext(Dispatchers.IO) {
            val response = getDrinkListService.getDrinkList(categoryName)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    DrinkListMapper.fromJsonToEntity(result)
                } else {
                    throw Exception()
                }
            } else {
                throw Exception()
            }
        }
    }
}