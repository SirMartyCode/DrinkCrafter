package com.sirmarty.drinkcrafter.core.drink.domain.entity

class DrinkDetail(
    val id: Int,
    val name: String,
    val category: String,
    val alcoholic: String,
    val glass: String,
    val instructions: String,
    val image: String,
    val ingredients: List<Ingredient>
)