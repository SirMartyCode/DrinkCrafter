package com.sirmarty.drinkcrafter.data.response

import com.google.gson.annotations.SerializedName
import com.sirmarty.drinkcrafter.domain.entity.IngredientName

data class IngredientListResponseJSON(
    @SerializedName("drinks") val ingredients: List<IngredientNameJSON>
)

fun IngredientListResponseJSON.toDomain(): List<IngredientName> {
    return this.ingredients.map { ingredient -> ingredient.toDomain() }
}

data class IngredientNameJSON(
    @SerializedName("strIngredient1") val name: String
)

fun IngredientNameJSON.toDomain(): IngredientName {
    return IngredientName(this.name)
}