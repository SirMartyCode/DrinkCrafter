package com.sirmarty.drinkcrafter.navigation

import androidx.annotation.DrawableRes
import com.sirmarty.drinkcrafter.R

enum class TopLevelDestination(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val text: String,

) {
    SEARCH(
        R.drawable.search,
        R.drawable.search_filled,
        "Search"

    ),
    HOME(
        R.drawable.home,
        R.drawable.home_filled,
        "Home"

    )
}

