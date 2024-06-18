package com.sirmarty.drinkcrafter.ui.navigation.toplevel

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavController.navigateToFindGraph(navOptions: NavOptions? = null) {
    this.navigate(Routes.Find.route, navOptions)
}

fun NavGraphBuilder.findGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = Routes.Find.route,
        startDestination = Routes.Search.route
    ) {
        nestedGraphs()
    }
}