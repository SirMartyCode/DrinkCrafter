package com.sirmarty.drinkcrafter.core.drink.data

import com.sirmarty.drinkcrafter.core.drink.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.core.drink.domain.entity.Ingredient

class DrinkDetailMapper {

    companion object {

        fun fromJsonToEntity(json: DrinkDetailResponseJSON): DrinkDetail {
            val drinkJSON = json.drinks[0]

            /***
             * The web service should return a list of objects containing ingredient
             * data (name, and measure). Since it returns a string field for each ingredient and
             * measure, we have to map it using the 'addIngredientIfNotNull()' method in order to
             * obtain the desired ingredient list
             */
            val ingredients: MutableList<Ingredient> = mutableListOf()
            addIngredientIfNotNull(drinkJSON.ingredient1, drinkJSON.measure1, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient2, drinkJSON.measure2, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient3, drinkJSON.measure3, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient4, drinkJSON.measure4, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient5, drinkJSON.measure5, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient6, drinkJSON.measure6, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient7, drinkJSON.measure7, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient8, drinkJSON.measure8, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient9, drinkJSON.measure9, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient10, drinkJSON.measure10, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient11, drinkJSON.measure11, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient12, drinkJSON.measure12, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient13, drinkJSON.measure13, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient14, drinkJSON.measure14, ingredients)
            addIngredientIfNotNull(drinkJSON.ingredient15, drinkJSON.measure15, ingredients)

            return DrinkDetail(
                id = drinkJSON.id,
                name = drinkJSON.name,
                category = drinkJSON.category,
                alcoholic = drinkJSON.alcoholic,
                glass = drinkJSON.glass,
                instructions = drinkJSON.instructions,
                image = drinkJSON.image,
                ingredients = ingredients
            )
        }

        // We want to avoid adding empty ingredient values to the list
        private fun addIngredientIfNotNull(ingredient: String?, measure: String?, list: MutableList<Ingredient>) {
            if (ingredient != null) {
                list.add(Ingredient(ingredient, measure))
            }
        }
    }
}