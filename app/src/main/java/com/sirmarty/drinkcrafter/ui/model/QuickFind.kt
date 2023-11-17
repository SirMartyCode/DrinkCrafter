package com.sirmarty.drinkcrafter.ui.model

import androidx.annotation.DrawableRes
import com.sirmarty.drinkcrafter.R

enum class QuickFind(val ingredient: String, @DrawableRes val image: Int) {
    Vodka("Vodka", R.drawable.ic_home),
    Tequila("Tequila", R.drawable.ic_home),
    Gin("Gin", R.drawable.ic_home),
    Rum("Rum", R.drawable.ic_home),
}