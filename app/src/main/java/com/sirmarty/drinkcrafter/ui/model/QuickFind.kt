package com.sirmarty.drinkcrafter.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.sirmarty.drinkcrafter.R

@Immutable
enum class QuickFind(val ingredient: String, @StringRes val text: Int, @DrawableRes val image: Int) {
    Rum("Rum", R.string.quick_find_rum, R.drawable.image_rum),
    Vodka("Vodka", R.string.quick_find_vodka, R.drawable.image_vodka),
    Tequila("Tequila", R.string.quick_find_tequila, R.drawable.image_tequila),
    Brandy("Brandy", R.string.quick_find_brandy, R.drawable.image_brandy),
    Gin("Gin", R.string.quick_find_gin, R.drawable.image_gin),
    Whiskey("Whiskey", R.string.quick_find_whiskey, R.drawable.image_whiskey),
}