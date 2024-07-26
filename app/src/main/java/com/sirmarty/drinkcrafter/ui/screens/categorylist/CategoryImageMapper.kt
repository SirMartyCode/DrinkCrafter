package com.sirmarty.drinkcrafter.ui.screens.categorylist

import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.ui.model.CategoryWithImage
import javax.inject.Inject

class CategoryImageMapper @Inject constructor() {

    private val ordinaryDrink = "Ordinary Drink"
    private val cocktail = "Cocktail"
    private val shake = "Shake"
    private val otherUnknown = "Other / Unknown"
    private val cocoa = "Cocoa"
    private val shot = "Shot"
    private val coffeeTea = "Coffee / Tea"
    private val homemadeLiqueur = "Homemade Liqueur"
    private val punchPartyDrink = "Punch / Party Drink"
    private val beer = "Beer"
    private val softDrink = "Soft Drink"

    private val categoryImageMap = mapOf(
        ordinaryDrink to R.drawable.image_ordinary_drink,
        cocktail to R.drawable.image_cocktail,
        shake to R.drawable.image_shake,
        otherUnknown to R.drawable.image_other_unknown,
        cocoa to R.drawable.image_cocoa,
        shot to R.drawable.image_shot,
        coffeeTea to R.drawable.image_coffe_tea,
        homemadeLiqueur to R.drawable.image_homemade_liqueur,
        punchPartyDrink to R.drawable.image_party_drink,
        beer to R.drawable.image_beer,
        softDrink to R.drawable.image_soft_drink
    )

    fun getCategoryWithImageForCategoryName(categoryName: String): CategoryWithImage {
        val image = categoryImageMap[categoryName] ?: R.drawable.image_default_category
        return CategoryWithImage(categoryName, image)
    }
}