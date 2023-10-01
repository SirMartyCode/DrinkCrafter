package com.sirmarty.drinkcrafter.core.drink.data

import com.sirmarty.drinkcrafter.core.drink.domain.entity.Drink

class DrinkListMapper {

    companion object {

        fun fromJsonToEntity(json: DrinkListResponseJSON): List<Drink> {
            val drinkList: MutableList<Drink> = mutableListOf()

            json.drinks.forEach { drinkJson ->
                drinkList.add(
                    Drink(
                        id = drinkJson.id,
                        name = drinkJson.name,
                        image = drinkJson.image)
                )
            }

            return drinkList
        }
    }
}