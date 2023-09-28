package com.sirmarty.drinkcrafter.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sirmarty.drinkcrafter.core.drink.presentation.DrinkDetailScreen

fun NavController.navigateToDrinkDetail(drinkId: Int) {
    this.navigate(Routes.DrinkDetail.createRoute(drinkId))
}

fun NavGraphBuilder.drinkDetailScreen() {
    composable(
        route = Routes.DrinkDetail.route,
        arguments = listOf(navArgument(Routes.DrinkDetail.drinkIdArg) {
            type = NavType.IntType
        })
    ) { backStackEntry ->
        val drinkId =
            backStackEntry.arguments?.getInt(Routes.DrinkDetail.drinkIdArg)
        if (drinkId != null) {
            DrinkDetailScreen(drinkId)
        }
    }
}