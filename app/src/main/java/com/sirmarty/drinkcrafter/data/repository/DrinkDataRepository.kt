package com.sirmarty.drinkcrafter.data.repository

import com.sirmarty.drinkcrafter.data.dao.DrinkDetailDao
import com.sirmarty.drinkcrafter.data.db.DrinkDetailDbMapper
import com.sirmarty.drinkcrafter.data.response.DrinkListResponseJSON
import com.sirmarty.drinkcrafter.data.response.toDomain
import com.sirmarty.drinkcrafter.data.service.GetDrinkDetailService
import com.sirmarty.drinkcrafter.data.service.GetDrinkListByIngredientService
import com.sirmarty.drinkcrafter.data.service.GetDrinkListService
import com.sirmarty.drinkcrafter.data.service.GetRandomDrinkDetailService
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DrinkDataRepository @Inject constructor(
    private val getDrinkDetailService: GetDrinkDetailService,
    private val getRandomDrinkDetailService: GetRandomDrinkDetailService,
    private val getDrinkListService: GetDrinkListService,
    private val getDrinkListByIngredientService: GetDrinkListByIngredientService,
    private val drinkDetailDao: DrinkDetailDao
) : DrinkRepository {

    override suspend fun getDrinkDetail(id: Int): DrinkDetail {
        return withContext(Dispatchers.IO) {
            val response = getDrinkDetailService.getDrinkDetail(id)
            if (response.isSuccessful) {
                val result = response.body()
                result?.toDomain() ?: throw Exception()
            } else {
                throw Exception()
            }
        }
    }

    override suspend fun getSavedDrinkDetail(id: Int): DrinkDetail {
        return withContext(Dispatchers.IO) {
            val result = drinkDetailDao.getById(id).first()
            DrinkDetailDbMapper.toDomain(result)
        }
    }

    override suspend fun getRandomDrinkDetail(): DrinkDetail {
        return withContext(Dispatchers.IO) {
            val response = getRandomDrinkDetailService.getRandomDrinkDetail()
            if (response.isSuccessful) {
                val result = response.body()
                result?.toDomain() ?: throw Exception()
            } else {
                throw Exception()
            }
        }
    }

    override suspend fun getDrinkList(categoryName: String): List<Drink> {
        return withContext(Dispatchers.IO) {
            val response = getDrinkListService.getDrinkList(categoryName)
            if (response.isSuccessful) {
                val result: DrinkListResponseJSON? = response.body()
                result?.toDomain() ?: throw Exception()
            } else {
                throw Exception()
            }
        }
    }

    override suspend fun getDrinkListByIngredient(ingredient: String): List<Drink> {
        return withContext(Dispatchers.IO) {
            val response = getDrinkListByIngredientService.getDrinkListByIngredient(ingredient)
            if (response.isSuccessful) {
                val result: DrinkListResponseJSON? = response.body()
                result?.toDomain() ?: throw Exception()
            } else {
                throw Exception()
            }
        }
    }

    override suspend fun saveDrinkDetail(drinkDetail: DrinkDetail) {
        withContext(Dispatchers.IO) {
            drinkDetailDao.save(DrinkDetailDbMapper.fromDomain(drinkDetail))
        }
    }

    override suspend fun deleteDrinkDetail(drinkId: Int) {
        withContext(Dispatchers.IO) {
            drinkDetailDao.delete(drinkId)
        }
    }

    override fun isSaved(id: Int): Flow<Boolean> {
        return drinkDetailDao.exists(id).flowOn(Dispatchers.IO)
    }

    override fun getAllSaved(): Flow<List<Drink>> {
        return drinkDetailDao.getAll()
            .map { items -> items.map { Drink(it.id, it.name, it.image) } }
    }
}