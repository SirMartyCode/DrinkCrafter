package com.sirmarty.drinkcrafter.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sirmarty.drinkcrafter.data.dao.DrinkDetailDao
import com.sirmarty.drinkcrafter.data.db.DrinkDetailDB
import com.sirmarty.drinkcrafter.data.db.IngredientConverter

@Database(entities = [DrinkDetailDB::class], version = 1)
@TypeConverters(IngredientConverter::class)
abstract class DrinkCrafterDatabase: RoomDatabase() {
    abstract fun drinkDetailDao(): DrinkDetailDao
}