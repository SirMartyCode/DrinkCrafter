package com.sirmarty.drinkcrafter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sirmarty.drinkcrafter.ui.screens.categories.categoriesScreen
import com.sirmarty.drinkcrafter.ui.screens.drinkdetail.drinkDetailScreen
import com.sirmarty.drinkcrafter.ui.screens.drinklist.drinkListScreen
import com.sirmarty.drinkcrafter.ui.screens.drinkdetail.navigateToDrinkDetail
import com.sirmarty.drinkcrafter.ui.screens.drinklist.navigateToDrinkList
import com.sirmarty.drinkcrafter.ui.screens.search.searchScreen
import com.sirmarty.drinkcrafter.ui.navigation.toplevel.homeGraph

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