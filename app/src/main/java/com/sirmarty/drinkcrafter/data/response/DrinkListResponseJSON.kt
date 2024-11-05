package com.sirmarty.drinkcrafter.data.response

import com.google.gson.annotations.SerializedName
import com.sirmarty.drinkcrafter.domain.entity.Drink

data class DrinkListResponseJSON(@SerializedName("drinks") val drinks: List<DrinkJSON>)

fun DrinkListResponseJSON.toDomain(): List<Drink> {
    val drinkList: MutableList<Drink> = mutableListOf()

    drinks.forEach { drinkJson -> drinkList.add(drinkJson.toDomain()) }

    return drinkList
}

class DrinkJSON(
    @SerializedName("idDrink") val id: Int,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val image: String
)

fun DrinkJSON.toDomain(): Drink =
    Drink(
        id = id,
        name = name,
        image = image
    )