package com.sirmarty.drinkcrafter.data.repository

import com.sirmarty.drinkcrafter.data.dao.DrinkDetailDao
import com.sirmarty.drinkcrafter.data.db.DrinkDetailDB
import com.sirmarty.drinkcrafter.data.response.DrinkDetailMapper
import com.sirmarty.drinkcrafter.data.response.DrinkListMapper
import com.sirmarty.drinkcrafter.data.service.GetDrinkDetailService
import com.sirmarty.drinkcrafter.data.service.GetDrinkListService
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DrinkDataRepository @Inject constructor(
    private val getDrinkDetailService: GetDrinkDetailService,
    private val getDrinkListService: GetDrinkListService,
    private val drinkDetailDao: DrinkDetailDao
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

    override fun getAllSaved(): Flow<List<Drink>> {
        return drinkDetailDao.getAll()
            .map { items -> items.map { Drink(it.id, it.name, it.image) } }
    }
}