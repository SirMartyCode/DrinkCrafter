package com.sirmarty.drinkcrafter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sirmarty.drinkcrafter.ui.navigation.toplevel.homeGraph
import com.sirmarty.drinkcrafter.ui.screens.drinkdetail.drinkDetailScreen
import com.sirmarty.drinkcrafter.ui.screens.drinkdetail.navigateToDrinkDetail

@Composable
fun MainNavHost() {
    val mainNavController = rememberNavController()
    NavHost(
        navController = mainNavController,
        startDestination = Routes.Home.route
    ) {
        homeGraph(onDrinkClick = mainNavController::navigateToDrinkDetail)
        drinkDetailScreen(onBackClick = { mainNavController.popBackStack() })
    }
}