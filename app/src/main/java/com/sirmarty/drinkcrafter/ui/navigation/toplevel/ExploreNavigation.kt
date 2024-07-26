package com.sirmarty.drinkcrafter.ui.navigation.toplevel

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToExploreGraph(navOptions: NavOptions? = null) {
    this.navigate(Routes.Explore.route, navOptions)
}

fun NavGraphBuilder.exploreGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = Routes.Explore.route,
        startDestination = Routes.CategoryList.route
    ) {
        nestedGraphs()
    }
}