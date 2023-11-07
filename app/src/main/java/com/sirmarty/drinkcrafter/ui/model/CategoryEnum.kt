package com.sirmarty.drinkcrafter.ui.model

import androidx.annotation.DrawableRes
import com.sirmarty.drinkcrafter.R

enum class CategoryEnum(val categoryName: String, @DrawableRes val image: Int) {
    OrdinaryDrink("Ordinary Drink", R.drawable.image_ordinary_drink),
    Cocktail("Cocktail", R.drawable.image_cocktail),
    Shake("Shake", R.drawable.image_shake),
    OtherUnknown("Other / Unknown", R.drawable.image_other_unknown),
    Cocoa("Cocoa", R.drawable.image_cocoa),
    Shot("Shot", R.drawable.image_shot),
    CoffeeTea("Coffee / Tea", R.drawable.image_coffe_tea),
    HomemadeLiqueur("Homemade Liqueur", R.drawable.image_homemade_liqueur),
    PunchPartyDrink("Punch / Party Drink", R.drawable.image_party_drink),
    Beer("Beer", R.drawable.image_beer),
    SoftDrink("Soft Drink", R.drawable.image_soft_drink),
}