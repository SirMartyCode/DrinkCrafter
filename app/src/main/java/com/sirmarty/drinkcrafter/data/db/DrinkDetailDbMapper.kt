package com.sirmarty.drinkcrafter.data.db

import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.entity.Ingredient

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

        fun toDomain(drinkDetailDB: DrinkDetailDB) =
            DrinkDetail(
                drinkDetailDB.id,
                drinkDetailDB.name,
                drinkDetailDB.category,
                drinkDetailDB.alcoholic,
                drinkDetailDB.glass,
                drinkDetailDB.instructions,
                drinkDetailDB.image,
                drinkDetailDB.ingredients.map {
                    Ingredient(it.name, it.measure)
                }
            )
    }
}