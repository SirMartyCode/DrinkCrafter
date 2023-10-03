package com.sirmarty.drinkcrafter.core.search.data

import com.sirmarty.drinkcrafter.core.drink.domain.entity.Drink
import com.sirmarty.drinkcrafter.core.search.domain.repository.SearchRepository
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