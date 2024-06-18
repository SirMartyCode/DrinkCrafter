package com.sirmarty.drinkcrafter.ui.navigation.toplevel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sirmarty.drinkcrafter.R

sealed class TopLevelDestination(
    val name: String,
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    @StringRes val stringRes: Int,
) {
    object Explore : TopLevelDestination(
        "explore",
        R.drawable.ic_home,
        R.drawable.ic_home_filled,
        R.string.navigation_bar_explore
    )
    object Find : TopLevelDestination(
        "find",
        R.drawable.ic_find,
        R.drawable.ic_find_filled,
        R.string.navigation_bar_search
    )
    object Saved : TopLevelDestination(
        "saved",
        R.drawable.ic_saved,
        R.drawable.ic_saved_filled,
        R.string.navigation_bar_saved
    )
}

