package com.sirmarty.drinkcrafter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sirmarty.drinkcrafter.ui.navigation.toplevel.homeGraph
import com.sirmarty.drinkcrafter.ui.screens.drinkdetail.drinkDetailScreen
import com.sirmarty.drinkcrafter.ui.screens.drinkdetail.navigateToDrinkDetail
import com.sirmarty.drinkcrafter.ui.screens.ingredientdetail.ingredientDetailScreen
import com.sirmarty.drinkcrafter.ui.screens.ingredientdetail.navigateToIngredientDetail

@Composable
fun MainNavHost() {
    val mainNavController = rememberNavController()
    NavHost(
        navController = mainNavController,
        startDestination = Routes.Home.route
    ) {
        homeGraph(
            onDrinkClick = mainNavController::navigateToDrinkDetail,
            onIngredientClick = mainNavController::navigateToIngredientDetail,
            onRandomCocktailClick = {
                mainNavController.navigateToDrinkDetail(Routes.DrinkDetail.ID_RANDOM_DRINK)
            }
        )
        drinkDetailScreen(
            onIngredientClick = mainNavController::navigateToIngredientDetail,
            onBackClick = { mainNavController.popBackStack() }
        )
        ingredientDetailScreen(
            onBackClick = { mainNavController.popBackStack() },
            onDrinkClick = mainNavController::navigateToDrinkDetail
        )
    }
}