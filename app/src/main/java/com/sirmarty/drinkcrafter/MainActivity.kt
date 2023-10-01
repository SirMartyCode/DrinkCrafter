package com.sirmarty.drinkcrafter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sirmarty.drinkcrafter.navigation.DrinkCrafterNavHost
import com.sirmarty.drinkcrafter.navigation.DrinkCrafterNavigationBar
import com.sirmarty.drinkcrafter.ui.theme.DrinkCrafterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrinkCrafterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { DrinkCrafterNavigationBar(navController) }
                    ) { innerPadding ->
                        DrinkCrafterNavHost(
                            navController = navController,
                            Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}