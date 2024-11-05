package com.sirmarty.drinkcrafter.ui.screens.ingredientdetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToIngredientDetail(ingredient: String) {
    this.navigate(Routes.IngredientDetail.createRoute(ingredient))
}

fun NavGraphBuilder.ingredientDetailScreen(
    onBackClick: () -> Unit,
    onDrinkClick: (Int) -> Unit,
) {
    composable(
        route = Routes.IngredientDetail.route,
        arguments = listOf(navArgument(Routes.IngredientDetail.ingredientNameArg) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val ingredient =
            backStackEntry.arguments?.getString(Routes.IngredientDetail.ingredientNameArg)
        ingredient?.let { IngredientDetailScreen(it, onBackClick, onDrinkClick) }
    }
}