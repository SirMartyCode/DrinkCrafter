package com.sirmarty.drinkcrafter.ui.navigation.toplevel

import androidx.annotation.DrawableRes
import com.sirmarty.drinkcrafter.R

sealed class TopLevelDestination(
    val name: String,
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    val text: String,
) {
    object Explore : TopLevelDestination(
        "explore",
        R.drawable.home,
        R.drawable.home_filled,
        "Explore"

    )
    object Search : TopLevelDestination(
        "search",
        R.drawable.search,
        R.drawable.search_filled,
        "Search"

    )
}

