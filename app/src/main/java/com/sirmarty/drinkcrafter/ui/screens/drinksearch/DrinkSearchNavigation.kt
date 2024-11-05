package com.sirmarty.drinkcrafter.ui.screens.drinksearch

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToDrinkSearch(navOptions: NavOptions? = null) {
    this.navigate(Routes.SearchBar.route, navOptions)
}

fun NavGraphBuilder.drinkSearchScreen(
    onDrinkClick: (Int) -> Unit,
) {
    composable(
        route = Routes.SearchBar.route
    ) {
        DrinkSearchScreen(onDrinkClick)
    }
}
