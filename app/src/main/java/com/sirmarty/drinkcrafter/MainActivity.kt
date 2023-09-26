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
import com.sirmarty.drinkcrafter.categories.presentation.CategoriesScreen
import com.sirmarty.drinkcrafter.categories.presentation.CategoriesViewModel
import com.sirmarty.drinkcrafter.drink.presentation.DrinkDetailScreen
import com.sirmarty.drinkcrafter.drink.presentation.DrinkDetailViewModel
import com.sirmarty.drinkcrafter.drink.presentation.DrinkListScreen
import com.sirmarty.drinkcrafter.drink.presentation.DrinkListViewModel
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
                        composable("categories") {
                            CategoriesScreen(
                                viewModel = categoriesViewModel
                            ) { navController.navigate("drinkList/$it") }
                        }
                        composable("drinkList/{categoryName}") { backStackEntry ->
                            val categoryName = backStackEntry.arguments?.getString("categoryName")
                            if (categoryName != null) {
                                DrinkListScreen(
                                    drinkListViewModel,
                                    categoryName
                                ) { navController.navigate("drinkDetail/$it") }
                            }
                        }
                        composable(
                            "drinkDetail/{drinkId}",
                            arguments = listOf(navArgument("drinkId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val drinkId = backStackEntry.arguments?.getInt("drinkId")
                            if (drinkId != null) {
                                DrinkDetailScreen(drinkDetailViewModel, drinkId)
                            }
                        }
                    }
                }
            }
        }
    }
}