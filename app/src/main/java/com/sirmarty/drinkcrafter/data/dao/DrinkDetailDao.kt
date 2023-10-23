package com.sirmarty.drinkcrafter.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sirmarty.drinkcrafter.data.db.DrinkDetailDB
import kotlinx.coroutines.flow.Flow

private const val TABLE_NAME = "drink_detail"

@Dao
interface DrinkDetailDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): Flow<List<DrinkDetailDB>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id == :id")
    fun getById(id: Int): Flow<DrinkDetailDB>

    @Query("SELECT EXISTS(SELECT * FROM $TABLE_NAME WHERE id = :id)")
    fun exists(id: Int): Flow<Boolean>

    @Insert
    fun save(drinkDetailDB: DrinkDetailDB)

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    fun delete(id: Int)
}