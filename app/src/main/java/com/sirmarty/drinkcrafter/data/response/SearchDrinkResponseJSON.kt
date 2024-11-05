package com.sirmarty.drinkcrafter.data.response

import com.google.gson.annotations.SerializedName
import com.sirmarty.drinkcrafter.domain.entity.Drink

data class SearchDrinkResponseJSON(@SerializedName("drinks") val drinks: List<SearchDrinkJSON>?)

fun SearchDrinkResponseJSON.toDomain(): List<Drink> {
    val drinkList: MutableList<Drink> = mutableListOf()

    drinks?.forEach { drinkJson -> drinkList.add(drinkJson.toDomain()) }

    return drinkList
}

data class SearchDrinkJSON(
    @SerializedName("idDrink") val id: Int,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val image: String
)

fun SearchDrinkJSON.toDomain(): Drink =
    Drink(
        id = id,
        name = name,
        image = image
    )