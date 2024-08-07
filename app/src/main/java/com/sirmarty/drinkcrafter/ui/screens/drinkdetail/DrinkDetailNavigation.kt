package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToDrinkDetail(drinkId: Int) {
    this.navigate(Routes.DrinkDetail.createRoute(drinkId))
}

fun NavGraphBuilder.drinkDetailScreen(
    onIngredientClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        route = Routes.DrinkDetail.route,
        arguments = listOf(navArgument(Routes.DrinkDetail.drinkIdArg) {
            type = NavType.IntType
        })
    ) { backStackEntry ->
        val drinkId =
            backStackEntry.arguments?.getInt(Routes.DrinkDetail.drinkIdArg)
        if (drinkId != null) {
            DrinkDetailScreen(drinkId, onIngredientClick, onBackClick)
        }
    }
}