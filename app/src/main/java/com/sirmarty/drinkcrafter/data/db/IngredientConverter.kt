package com.sirmarty.drinkcrafter.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Since Room cannot store non primitive type values and we don't want to create a specific
 * table to store ingredient data, we'll save it as a json. To do it so we've defined an
 * IngredientConverter class that transforms a list of ingredients to json format string
 **/
class IngredientConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<IngredientDB> {
        val listType = object : TypeToken<List<IngredientDB>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toString(value: List<IngredientDB>): String {
        return gson.toJson(value)
    }
}