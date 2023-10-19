package com.sirmarty.drinkcrafter.data.db

import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail

class DrinkDetailDbMapper {
    companion object {
        fun fromDomain(drinkDetail: DrinkDetail) =
            DrinkDetailDB(
                drinkDetail.id,
                drinkDetail.name,
                drinkDetail.category,
                drinkDetail.alcoholic,
                drinkDetail.glass,
                drinkDetail.instructions,
                drinkDetail.image,
                drinkDetail.ingredients.map {
                    IngredientDB(it.name, it.measure)
                }
            )
    }
}