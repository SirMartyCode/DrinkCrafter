package com.sirmarty.drinkcrafter.ui.screens.ingredient

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToIngredient(ingredient: String) {
    this.navigate(Routes.Ingredient.createRoute(ingredient))
}

fun NavGraphBuilder.ingredientScreen(onBackClick: () -> Unit) {
    composable(
        route = Routes.Ingredient.route,
        arguments = listOf(navArgument(Routes.Ingredient.ingredientNameArg) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val ingredient =
            backStackEntry.arguments?.getString(Routes.Ingredient.ingredientNameArg)
        ingredient?.let { IngredientScreen(it, onBackClick) }
    }
}