package com.sirmarty.drinkcrafter.ui.screens.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(Routes.Search.route, navOptions)
}

fun NavGraphBuilder.searchScreen(onDrinkClick: (Int) -> Unit) {
    composable(
        route = Routes.Search.route
    ) {
        SearchScreen(onDrinkClick)
    }
}