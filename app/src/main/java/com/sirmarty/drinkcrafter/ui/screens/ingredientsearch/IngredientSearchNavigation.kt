package com.sirmarty.drinkcrafter.ui.screens.ingredientsearch

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToIngredientSearch(navOptions: NavOptions? = null) {
    this.navigate(Routes.IngredientSearch.route, navOptions)
}

fun NavGraphBuilder.ingredientSearchScreen(onIngredientClick: (String) -> Unit) {
    composable(
        route = Routes.IngredientSearch.route
    ) {
        IngredientSearchScreen(onIngredientClick)
    }
}