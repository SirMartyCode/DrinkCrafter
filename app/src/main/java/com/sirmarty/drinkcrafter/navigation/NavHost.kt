package com.sirmarty.drinkcrafter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sirmarty.drinkcrafter.core.categories.presentation.navigation.categoriesScreen
import com.sirmarty.drinkcrafter.core.drink.presentation.navigation.drinkDetailScreen
import com.sirmarty.drinkcrafter.core.drink.presentation.navigation.drinkListScreen
import com.sirmarty.drinkcrafter.core.drink.presentation.navigation.navigateToDrinkDetail
import com.sirmarty.drinkcrafter.core.drink.presentation.navigation.navigateToDrinkList
import com.sirmarty.drinkcrafter.core.search.presentation.searchScreen
import com.sirmarty.drinkcrafter.navigation.toplevel.homeGraph

@Composable
fun DrinkCrafterNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Routes.Home.route
    ) {
        searchScreen(onDrinkClick = navController::navigateToDrinkDetail)
        homeGraph(
            nestedGraphs = {
                categoriesScreen(onCategoryClick = navController::navigateToDrinkList)
                drinkListScreen(onDrinkClick = navController::navigateToDrinkDetail)
                drinkDetailScreen()
            })
    }
}