package com.sirmarty.drinkcrafter.ui.screens.drinklist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToDrinkList(categoryName: String) {
    this.navigate(Routes.DrinkList.createRoute(categoryName))
}

fun NavGraphBuilder.drinkListScreen(onDrinkClick: (Int) -> Unit) {
    composable(
        route = Routes.DrinkList.route,
        arguments = listOf(navArgument(Routes.DrinkList.categoryNameArg) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val categoryName =
            backStackEntry.arguments?.getString(Routes.DrinkList.categoryNameArg)
        if (categoryName != null) {
            DrinkListScreen(categoryName, onDrinkClick)
        }
    }
}
