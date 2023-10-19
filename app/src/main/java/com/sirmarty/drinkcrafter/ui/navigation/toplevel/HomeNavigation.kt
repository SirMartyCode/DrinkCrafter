package com.sirmarty.drinkcrafter.ui.navigation.toplevel

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sirmarty.drinkcrafter.ui.navigation.DrinkCrafterNavigationBar
import com.sirmarty.drinkcrafter.ui.navigation.Routes
import com.sirmarty.drinkcrafter.ui.screens.categories.categoriesScreen
import com.sirmarty.drinkcrafter.ui.screens.drinklist.drinkListScreen
import com.sirmarty.drinkcrafter.ui.screens.drinklist.navigateToDrinkList
import com.sirmarty.drinkcrafter.ui.screens.saved.savedScreen
import com.sirmarty.drinkcrafter.ui.screens.search.searchScreen

fun NavGraphBuilder.homeGraph(onDrinkClick: (Int) -> Unit) {
    composable(
        route = Routes.Home.route
    ) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { DrinkCrafterNavigationBar(navController) }
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = Routes.Explore.route,
            ) {
                searchScreen(onDrinkClick = onDrinkClick)
                exploreGraph(
                    nestedGraphs = {
                        categoriesScreen(onCategoryClick = navController::navigateToDrinkList)
                        drinkListScreen(onDrinkClick = onDrinkClick)
                    })
                savedScreen(onDrinkClick = onDrinkClick)
            }
        }
    }
}