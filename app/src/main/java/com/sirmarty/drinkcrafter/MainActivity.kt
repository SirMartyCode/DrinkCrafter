package com.sirmarty.drinkcrafter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sirmarty.drinkcrafter.core.categories.presentation.CategoriesScreen
import com.sirmarty.drinkcrafter.core.categories.presentation.CategoriesViewModel
import com.sirmarty.drinkcrafter.core.drink.presentation.DrinkDetailScreen
import com.sirmarty.drinkcrafter.core.drink.presentation.DrinkDetailViewModel
import com.sirmarty.drinkcrafter.core.drink.presentation.DrinkListScreen
import com.sirmarty.drinkcrafter.core.drink.presentation.DrinkListViewModel
import com.sirmarty.drinkcrafter.navigation.Routes
import com.sirmarty.drinkcrafter.ui.theme.DrinkCrafterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val categoriesViewModel: CategoriesViewModel by viewModels()
    private val drinkListViewModel: DrinkListViewModel by viewModels()
    private val drinkDetailViewModel: DrinkDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrinkCrafterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "categories") {
                        composable(Routes.Categories.route) {
                            CategoriesScreen(
                                viewModel = categoriesViewModel
                            ) { navController.navigate(Routes.DrinkList.createRoute(it)) }
                        }
                        composable(Routes.DrinkList.route) { backStackEntry ->
                            val categoryName =
                                backStackEntry.arguments?.getString(Routes.DrinkList.categoryNameArg)
                            if (categoryName != null) {
                                DrinkListScreen(
                                    viewModel = drinkListViewModel,
                                    categoryName = categoryName
                                ) { navController.navigate(Routes.DrinkDetail.createRoute(it)) }
                            }
                        }
                        composable(
                            Routes.DrinkDetail.route,
                            arguments = listOf(navArgument(Routes.DrinkDetail.drinkIdArg) {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val drinkId =
                                backStackEntry.arguments?.getInt(Routes.DrinkDetail.drinkIdArg)
                            if (drinkId != null) {
                                DrinkDetailScreen(
                                    viewModel = drinkDetailViewModel,
                                    drinkId = drinkId
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}