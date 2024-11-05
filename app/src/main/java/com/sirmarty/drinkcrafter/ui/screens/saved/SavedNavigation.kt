package com.sirmarty.drinkcrafter.ui.screens.saved

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToSaved(navOptions: NavOptions? = null) {
    this.navigate(Routes.Saved.route, navOptions)
}

fun NavGraphBuilder.savedScreen(onDrinkClick: (Int) -> Unit) {
    composable(
        route = Routes.Saved.route
    ) {
        SavedScreen(onDrinkClick)
    }
}