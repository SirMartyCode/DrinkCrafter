package com.sirmarty.drinkcrafter.data.response

import com.google.gson.annotations.SerializedName
import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.entity.Ingredient

data class DrinkDetailResponseJSON(@SerializedName("drinks") val drinks: List<DrinkDetailJSON>)

fun DrinkDetailResponseJSON.toDomain(): DrinkDetail {
    val drinkJSON = drinks[0]
    return drinkJSON.toDomain()
}

data class DrinkDetailJSON(
    @SerializedName("idDrink") val id: Int,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strAlcoholic") val alcoholic: String,
    @SerializedName("strGlass") val glass: String,
    @SerializedName("strInstructions") val instructions: String,
    @SerializedName("strDrinkThumb") val image: String,
    @SerializedName("strIngredient1") val ingredient1: String,
    @SerializedName("strIngredient2") val ingredient2: String,
    @SerializedName("strIngredient3") val ingredient3: String,
    @SerializedName("strIngredient4") val ingredient4: String,
    @SerializedName("strIngredient5") val ingredient5: String,
    @SerializedName("strIngredient6") val ingredient6: String,
    @SerializedName("strIngredient7") val ingredient7: String,
    @SerializedName("strIngredient8") val ingredient8: String,
    @SerializedName("strIngredient9") val ingredient9: String,
    @SerializedName("strIngredient10") val ingredient10: String,
    @SerializedName("strIngredient11") val ingredient11: String,
    @SerializedName("strIngredient12") val ingredient12: String,
    @SerializedName("strIngredient13") val ingredient13: String,
    @SerializedName("strIngredient14") val ingredient14: String,
    @SerializedName("strIngredient15") val ingredient15: String,
    @SerializedName("strMeasure1") val measure1: String,
    @SerializedName("strMeasure2") val measure2: String,
    @SerializedName("strMeasure3") val measure3: String,
    @SerializedName("strMeasure4") val measure4: String,
    @SerializedName("strMeasure5") val measure5: String,
    @SerializedName("strMeasure6") val measure6: String,
    @SerializedName("strMeasure7") val measure7: String,
    @SerializedName("strMeasure8") val measure8: String,
    @SerializedName("strMeasure9") val measure9: String,
    @SerializedName("strMeasure10") val measure10: String,
    @SerializedName("strMeasure11") val measure11: String,
    @SerializedName("strMeasure12") val measure12: String,
    @SerializedName("strMeasure13") val measure13: String,
    @SerializedName("strMeasure14") val measure14: String,
    @SerializedName("strMeasure15") val measure15: String
) {
    fun addIngredientIfNotNull(ingredient: String?, measure: String?, list: MutableList<Ingredient>) {
        if (ingredient != null) {
            list.add(Ingredient(ingredient.trim(), measure?.trim()))
        }
    }
}

fun DrinkDetailJSON.toDomain(): DrinkDetail {
    /***
     * The web service should return a list of objects containing ingredient
     * data (name, and measure). Since it returns a string field for each ingredient and
     * measure, we have to map it using the 'addIngredientIfNotNull()' method in order to
     * obtain the desired ingredient list
     */
    val ingredients: MutableList<Ingredient> = mutableListOf()
    addIngredientIfNotNull(ingredient1, measure1, ingredients)
    addIngredientIfNotNull(ingredient2, measure2, ingredients)
    addIngredientIfNotNull(ingredient3, measure3, ingredients)
    addIngredientIfNotNull(ingredient4, measure4, ingredients)
    addIngredientIfNotNull(ingredient5, measure5, ingredients)
    addIngredientIfNotNull(ingredient6, measure6, ingredients)
    addIngredientIfNotNull(ingredient7, measure7, ingredients)
    addIngredientIfNotNull(ingredient8, measure8, ingredients)
    addIngredientIfNotNull(ingredient9, measure9, ingredients)
    addIngredientIfNotNull(ingredient10, measure10, ingredients)
    addIngredientIfNotNull(ingredient11, measure11, ingredients)
    addIngredientIfNotNull(ingredient12, measure12, ingredients)
    addIngredientIfNotNull(ingredient13, measure13, ingredients)
    addIngredientIfNotNull(ingredient14, measure14, ingredients)
    addIngredientIfNotNull(ingredient15, measure15, ingredients)

    return DrinkDetail(
        id = id,
        name = name,
        category = category,
        alcoholic = alcoholic,
        glass = glass,
        instructions = instructions,
        image = image,
        ingredients = ingredients
    )
}