package com.sirmarty.drinkcrafter.data.response

import com.sirmarty.drinkcrafter.domain.entity.Drink

class SearchDrinkMapper {

    companion object {

        fun fromJsonToEntity(json: SearchDrinkResponseJSON): List<Drink> {
            val drinkList: MutableList<Drink> = mutableListOf()

            json.drinks?.forEach { drinkJson ->
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