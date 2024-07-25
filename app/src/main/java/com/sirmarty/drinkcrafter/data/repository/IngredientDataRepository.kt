package com.sirmarty.drinkcrafter.data.repository

import com.sirmarty.drinkcrafter.data.response.toDomain
import com.sirmarty.drinkcrafter.data.service.GetIngredientDetailService
import com.sirmarty.drinkcrafter.data.service.GetIngredientListService
import com.sirmarty.drinkcrafter.domain.entity.IngredientDetail
import com.sirmarty.drinkcrafter.domain.entity.IngredientName
import com.sirmarty.drinkcrafter.domain.repository.IngredientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IngredientDataRepository @Inject constructor(
    private val getIngredientDetailService: GetIngredientDetailService,
    private val getIngredientListService: GetIngredientListService
): IngredientRepository {
    override suspend fun getIngredientDetail(name: String): IngredientDetail {
        return withContext(Dispatchers.IO) {
            val response = getIngredientDetailService.getIngredientDetail(name)
            if (response.isSuccessful) {
                val result = response.body()
                result?.toDomain() ?: throw Exception()
            } else {
                throw Exception()
            }
        }
    }

    override suspend fun getIngredientList(): List<IngredientName> {
        return withContext(Dispatchers.IO) {
            val response = getIngredientListService.getIngredientList()
            if (response.isSuccessful){
                val result = response.body()
                result?.toDomain() ?: throw Exception()
            } else {
                throw Exception()
            }
        }
    }
}