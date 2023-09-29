package com.sirmarty.drinkcrafter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun DrinkCrafterNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Routes.Home.route
    ) {
        searchScreen()
        homeGraph(
            nestedGraphs = {
                categoriesScreen(onCategoryClick = navController::navigateToDrinkList)
                drinkListScreen(onDrinkClick = navController::navigateToDrinkDetail)
                drinkDetailScreen()
            })
    }
}