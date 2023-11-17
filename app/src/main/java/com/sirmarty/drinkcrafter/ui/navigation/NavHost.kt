package com.sirmarty.drinkcrafter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sirmarty.drinkcrafter.ui.navigation.toplevel.homeGraph
import com.sirmarty.drinkcrafter.ui.screens.drinkdetail.drinkDetailScreen
import com.sirmarty.drinkcrafter.ui.screens.drinkdetail.navigateToDrinkDetail
import com.sirmarty.drinkcrafter.ui.screens.ingredient.ingredientScreen
import com.sirmarty.drinkcrafter.ui.screens.ingredient.navigateToIngredient

@Composable
fun MainNavHost() {
    val mainNavController = rememberNavController()
    NavHost(
        navController = mainNavController,
        startDestination = Routes.Home.route
    ) {
        homeGraph(
            onDrinkClick = mainNavController::navigateToDrinkDetail,
            onIngredientClick = mainNavController::navigateToIngredient
        )
        drinkDetailScreen(onBackClick = { mainNavController.popBackStack() })
        ingredientScreen(onBackClick = { mainNavController.popBackStack() })
    }
}