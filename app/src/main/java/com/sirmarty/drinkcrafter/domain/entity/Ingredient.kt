package com.sirmarty.drinkcrafter.domain.entity

data class Ingredient(
    val name: String,
    val measure: String?
) {
    override fun toString(): String {
        return if (measure != null) {
            "$measure $name"
        } else {
            name
        }
    }
}