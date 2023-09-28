package com.sirmarty.drinkcrafter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun DrinkCrafterNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Categories.route
    ) {
        categoriesScreen(onCategoryClick = navController::navigateToDrinkList)
        drinkListScreen(onDrinkClick = navController::navigateToDrinkDetail)
        drinkDetailScreen()
    }
}