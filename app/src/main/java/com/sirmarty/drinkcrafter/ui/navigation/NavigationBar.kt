package com.sirmarty.drinkcrafter.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.sirmarty.drinkcrafter.ui.navigation.toplevel.TopLevelDestination
import com.sirmarty.drinkcrafter.ui.navigation.toplevel.navigateToExploreGraph
import com.sirmarty.drinkcrafter.ui.screens.saved.navigateToSaved
import com.sirmarty.drinkcrafter.ui.screens.search.navigateToSearch

@Composable
fun DrinkCrafterNavigationBar(navController: NavHostController) {
    NavigationBar {
        val items = listOf(
            TopLevelDestination.Explore,
            TopLevelDestination.Search,
            TopLevelDestination.Saved
        )
        items.forEach { item ->
            val currentDestination = navController.currentBackStackEntryAsState().value?.destination
            val selected = currentDestination.isTopLevelDestinationInHierarchy(item)
            DrinkCrafterNavigationItem(
                item = item,
                onClick = {
                    navigateToTopLevelDestination(navController, item)
                },
                selected = selected,
                selectedIcon = {
                    Icon(
                        painter = painterResource(item.selectedIcon),
                        contentDescription = "selected icon"
                    )
                },
                unselectedIcon = {
                    Icon(
                        painter = painterResource(item.unselectedIcon),
                        contentDescription = "unselected icon"
                    )
                }
            )
        }
    }
}

@Composable
fun RowScope.DrinkCrafterNavigationItem(
    item: TopLevelDestination,
    onClick: () -> Unit,
    selected: Boolean,
    selectedIcon: @Composable () -> Unit,
    unselectedIcon: @Composable () -> Unit
) {
    NavigationBarItem(
        icon = if (selected) selectedIcon else unselectedIcon,
        onClick = onClick,
        label = { Text(item.text) },
        selected = selected
    )
}

//==================================================================================================
//region Navigation methods

private fun navigateToTopLevelDestination(
    navController: NavHostController,
    topLevelDestination: TopLevelDestination
) {
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
        TopLevelDestination.Explore -> navController.navigateToExploreGraph(topLevelNavOptions)
        TopLevelDestination.Search -> navController.navigateToSearch(topLevelNavOptions)
        TopLevelDestination.Saved -> navController.navigateToSaved(topLevelNavOptions)
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
    return this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
}

//endregion