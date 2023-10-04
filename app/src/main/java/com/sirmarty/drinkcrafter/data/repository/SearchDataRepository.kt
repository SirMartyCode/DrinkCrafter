package com.sirmarty.drinkcrafter.data.repository

import com.sirmarty.drinkcrafter.data.response.SearchDrinkMapper
import com.sirmarty.drinkcrafter.data.service.SearchDrinkByNameService
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchDataRepository @Inject constructor(
    private val searchDrinkByNameService: SearchDrinkByNameService
) : SearchRepository {
    override suspend fun searchDrinkByName(text: String): List<Drink> {
        return withContext(Dispatchers.IO) {
            val response = searchDrinkByNameService.searchDrinkByName(text)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    SearchDrinkMapper.fromJsonToEntity(result)
                } else {
                    throw Exception()
                }
            } else {
                throw Exception()
            }
        }
    }
}