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
        R.drawable.ic_home,
        R.drawable.ic_home_filled,
        "Explore"

    )
    object Search : TopLevelDestination(
        "search",
        R.drawable.ic_search,
        R.drawable.ic_search_filled,
        "Search"

    )
    object Saved : TopLevelDestination(
        "saved",
        R.drawable.ic_saved,
        R.drawable.ic_saved_filled,
        "Saved"
    )
}

