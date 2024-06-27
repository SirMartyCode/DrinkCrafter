package com.sirmarty.drinkcrafter.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sirmarty.drinkcrafter.R

enum class QuickFind(val ingredient: String, @StringRes val text: Int, @DrawableRes val image: Int) {
    Rum("Rum", R.string.quick_find_rum, R.drawable.ic_rum),
    Vodka("Vodka", R.string.quick_find_vodka, R.drawable.ic_vodka),
    Tequila("Tequila", R.string.quick_find_tequila, R.drawable.ic_tequila),
    Gin("Gin", R.string.quick_find_gin, R.drawable.ic_gin),
    Whiskey("Whiskey", R.string.quick_find_whiskey, R.drawable.ic_whiskey),
    Jagermeister("Jagermeister", R.string.quick_find_jager, R.drawable.ic_jagermeister),
}