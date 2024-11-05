package com.sirmarty.drinkcrafter.domain.entity

data class IngredientDetail(
    val id: Int,
    val name: String,
    val description: String?,
    val type: String?,
    val alcohol: Boolean,
    val abv: String?
)