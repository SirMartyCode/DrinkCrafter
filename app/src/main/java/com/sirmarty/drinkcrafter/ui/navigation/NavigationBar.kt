package com.sirmarty.drinkcrafter.ui.navigation

import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.ui.navigation.toplevel.TopLevelDestination
import com.sirmarty.drinkcrafter.ui.navigation.toplevel.navigateToExploreGraph
import com.sirmarty.drinkcrafter.ui.navigation.toplevel.navigateToFindGraph
import com.sirmarty.drinkcrafter.ui.screens.saved.navigateToSaved

@Composable
fun DrinkCrafterNavigationBar(navController: NavHostController) {
    val context = LocalContext.current

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        val items = listOf(
            TopLevelDestination.Explore,
            TopLevelDestination.Find,
            TopLevelDestination.Saved
        )
        items.forEach { item ->
            val currentDestination = navController.currentBackStackEntryAsState().value?.destination
            val selected = currentDestination.isTopLevelDestinationInHierarchy(item)
            DrinkCrafterNavigationItem(
                context,
                item = item,
                onClick = {
                    navigateToTopLevelDestination(navController, item)
                },
                selected = selected,
                selectedIcon = {
                    Icon(
                        painter = painterResource(item.selectedIcon),
                        modifier = Modifier.size(24.dp),
                        contentDescription = context.getString(R.string.navigation_bar_item_selected_icon),
                    )
                },
                unselectedIcon = {
                    Icon(
                        painter = painterResource(item.unselectedIcon),
                        modifier = Modifier.size(24.dp),
                        contentDescription = context.getString(R.string.navigation_bar_item_unselected_icon),
                    )
                }
            )
        }
    }
}

@Composable
fun RowScope.DrinkCrafterNavigationItem(
    context: Context,
    item: TopLevelDestination,
    onClick: () -> Unit,
    selected: Boolean,
    selectedIcon: @Composable () -> Unit,
    unselectedIcon: @Composable () -> Unit
) {
    NavigationBarItem(
        icon = if (selected) selectedIcon else unselectedIcon,
        onClick = onClick,
        label = { Text(context.getString(item.stringRes)) },
        selected = selected,
        colors = NavigationBarItemColors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
            selectedIndicatorColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
            unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
            // It should never be disabled
            disabledIconColor = Color.Unspecified,
            disabledTextColor = Color.Unspecified
        )
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
        TopLevelDestination.Find -> navController.navigateToFindGraph(topLevelNavOptions)
        TopLevelDestination.Saved -> navController.navigateToSaved(topLevelNavOptions)
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
    return this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
}

//endregion