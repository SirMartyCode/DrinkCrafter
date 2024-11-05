package com.sirmarty.drinkcrafter.ui.screens.categorylist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavGraphBuilder.categoryListScreen(onCategoryClick: (String) -> Unit) {
    composable(
        route = Routes.CategoryList.route
    ) {
        CategoryListScreen(onCategoryClick)
    }
}