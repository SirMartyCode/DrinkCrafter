package com.sirmarty.drinkcrafter.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sirmarty.drinkcrafter.core.categories.presentation.CategoriesScreen

fun NavGraphBuilder.categoriesScreen(onCategoryClick: (String) -> Unit) {
    composable(
        route = Routes.Categories.route
    ) {
        CategoriesScreen(onCategoryClick)
    }
}