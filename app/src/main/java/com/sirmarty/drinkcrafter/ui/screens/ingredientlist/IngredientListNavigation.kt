package com.sirmarty.drinkcrafter.ui.screens.ingredientlist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToIngredientList(navOptions: NavOptions? = null) {
    this.navigate(Routes.IngredientList.route, navOptions)
}

fun NavGraphBuilder.ingredientListScreen(onIngredientClick: (String) -> Unit) {
    composable(
        route = Routes.IngredientList.route
    ) {
        IngredientListScreen(onIngredientClick)
    }
}