package com.sirmarty.drinkcrafter.data.response

import com.google.gson.annotations.SerializedName
import com.sirmarty.drinkcrafter.domain.entity.IngredientDetail

data class IngredientDetailResponseJSON(@SerializedName("ingredients") val ingredients: List<IngredientDetailJSON>)

fun IngredientDetailResponseJSON.toDomain(): IngredientDetail {
    val ingredientJSON = ingredients[0]
    return ingredientJSON.toDomain()
}

data class IngredientDetailJSON(
    @SerializedName("idIngredient") val id: Int,
    @SerializedName("strIngredient") val name: String,
    @SerializedName("strDescription") val description: String,
    @SerializedName("strType") val type: String,
    @SerializedName("strAlcohol") val alcohol: String,
    @SerializedName("strABV") val abv: String,
)

private fun IngredientDetailJSON.toDomain(): IngredientDetail {
    return IngredientDetail(
        id = id,
        name = name,
        description = description,
        type = type,
        alcohol = (alcohol == "Yes"),
        abv = abv
    )
}