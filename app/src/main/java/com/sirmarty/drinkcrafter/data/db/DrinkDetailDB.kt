package com.sirmarty.drinkcrafter.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink_detail")
data class DrinkDetailDB(
    @PrimaryKey val id: Int,
    val name: String,
    val category: String,
    val alcoholic: String,
    val glass: String,
    val instructions: String,
    val image: String,
    val ingredients: List<IngredientDB>
)