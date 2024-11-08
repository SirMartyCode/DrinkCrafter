package com.sirmarty.drinkcrafter.ui.navigation.toplevel

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sirmarty.drinkcrafter.ui.navigation.DrinkCrafterNavigationBar
import com.sirmarty.drinkcrafter.ui.navigation.Routes
import com.sirmarty.drinkcrafter.ui.screens.categorydetail.categoryDetailScreen
import com.sirmarty.drinkcrafter.ui.screens.categorydetail.navigateToCategoryDetail
import com.sirmarty.drinkcrafter.ui.screens.categorylist.categoryListScreen
import com.sirmarty.drinkcrafter.ui.screens.drinksearch.drinkSearchScreen
import com.sirmarty.drinkcrafter.ui.screens.drinksearch.navigateToDrinkSearch
import com.sirmarty.drinkcrafter.ui.screens.ingredientsearch.ingredientSearchScreen
import com.sirmarty.drinkcrafter.ui.screens.ingredientsearch.navigateToIngredientSearch
import com.sirmarty.drinkcrafter.ui.screens.saved.savedScreen
import com.sirmarty.drinkcrafter.ui.screens.search.searchScreen

fun NavGraphBuilder.homeGraph(
    onDrinkClick: (Int) -> Unit,
    onIngredientClick: (String) -> Unit,
    onRandomCocktailClick: () -> Unit
) {
    composable(
        route = Routes.Home.route
    ) {

        // Set status bar to default color
        val context = LocalContext.current
        val view = LocalView.current
        val window = (context as? Activity)?.window
        val darkTheme = isSystemInDarkTheme()

        val windowInsetsController = window?.let { WindowInsetsControllerCompat(it, view) }
        windowInsetsController?.isAppearanceLightStatusBars = !darkTheme

        val navController = rememberNavController()
        Scaffold(
            bottomBar = { DrinkCrafterNavigationBar(navController) }
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = Routes.Explore.route,
            ) {
                exploreGraph(
                    nestedGraphs = {
                        categoryListScreen(onCategoryClick = navController::navigateToCategoryDetail)
                        categoryDetailScreen(onDrinkClick = onDrinkClick)
                    })
                findGraph(nestedGraphs = {
                    searchScreen(
                        onQuickFindClick = onIngredientClick,
                        onSearchByNameClick = navController::navigateToDrinkSearch,
                        onSearchByIngredientClick = navController::navigateToIngredientSearch,
                        onRandomCocktailClick = onRandomCocktailClick
                    )
                    drinkSearchScreen(onDrinkClick = onDrinkClick)
                    ingredientSearchScreen(onIngredientClick = onIngredientClick)
                })
                savedScreen(onDrinkClick = onDrinkClick)
            }
        }
    }
}