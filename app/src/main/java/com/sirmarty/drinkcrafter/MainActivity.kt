package com.sirmarty.drinkcrafter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sirmarty.drinkcrafter.navigation.DrinkCrafterNavHost
import com.sirmarty.drinkcrafter.navigation.TopLevelDestination
import com.sirmarty.drinkcrafter.navigation.navigateToHomeGraph
import com.sirmarty.drinkcrafter.navigation.navigateToSearch
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
                    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
                    Scaffold(
                        bottomBar = {
                            val items = TopLevelDestination.values().asList()
                            NavigationBar(tonalElevation = 5.dp) {
                                items.forEach { item ->

                                    val selected =
                                        currentDestination.isTopLevelDestinationInHierarchy(item)
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                imageVector = Icons.Filled.Favorite,
                                                contentDescription = null
                                            )
                                            /*
                                            if (selected) {
                                                Icon(
                                                    painterResource(item.selectedIcon),
                                                    contentDescription = null
                                                )
                                            } else {
                                                Icon(
                                                    painterResource(item.icon),
                                                    contentDescription = null
                                                )
                                            }

                                             */
                                        },
                                        label = { Text(item.text) },
                                        selected = selected,
                                        onClick = { navigateToTopLevelDestination(navController, item)
                                        }
                                    )
                                }
                            }
                        }
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

    private fun navigateToTopLevelDestination(navController: NavHostController, topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.SEARCH -> navController.navigateToSearch(topLevelNavOptions)
            TopLevelDestination.HOME -> navController.navigateToHomeGraph(topLevelNavOptions)
        }
    }

    private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
        return this?.hierarchy?.any {
            it.route?.contains(destination.name, true) ?: false
        } ?: false
    }

}