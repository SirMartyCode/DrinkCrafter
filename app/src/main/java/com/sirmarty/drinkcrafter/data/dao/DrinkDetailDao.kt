package com.sirmarty.drinkcrafter.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.sirmarty.drinkcrafter.data.db.DrinkDetailDB
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDetailDao {
    @Query("SELECT * FROM drink_detail")
    fun getAll(): Flow<List<DrinkDetailDB>>
}