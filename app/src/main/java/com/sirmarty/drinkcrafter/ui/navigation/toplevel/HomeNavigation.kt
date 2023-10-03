package com.sirmarty.drinkcrafter.ui.navigation.toplevel

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(Routes.Home.route, navOptions)
}

fun NavGraphBuilder.homeGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = Routes.Home.route,
        startDestination = Routes.Categories.route
    ) {
        nestedGraphs()
    }
}