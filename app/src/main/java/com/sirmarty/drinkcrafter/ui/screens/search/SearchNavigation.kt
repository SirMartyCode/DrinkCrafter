package com.sirmarty.drinkcrafter.ui.screens.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavGraphBuilder.searchScreen(
    onQuickFindClick: (String) -> Unit,
    onSearchByNameClick: () -> Unit,
    onRandomCocktailClick: () -> Unit
) {
    composable(
        route = Routes.Search.route
    ) {
        SearchScreen(onQuickFindClick, onSearchByNameClick, onRandomCocktailClick)
    }
}