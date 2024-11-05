package com.sirmarty.drinkcrafter.ui.screens.categorydetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToCategoryDetail(categoryName: String) {
    this.navigate(Routes.CategoryDetail.createRoute(categoryName))
}

fun NavGraphBuilder.categoryDetailScreen(onDrinkClick: (Int) -> Unit) {
    composable(
        route = Routes.CategoryDetail.route,
        arguments = listOf(navArgument(Routes.CategoryDetail.categoryNameArg) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val categoryNameArg =
            backStackEntry.arguments?.getString(Routes.CategoryDetail.categoryNameArg)
        val categoryName = Routes.CategoryDetail.getArgumentValue(categoryNameArg)
        categoryName?.let { CategoryDetailScreen(categoryName, onDrinkClick) }
    }
}
