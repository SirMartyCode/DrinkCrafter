package com.sirmarty.drinkcrafter.core.categories.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sirmarty.drinkcrafter.core.categories.presentation.CategoriesScreen
import com.sirmarty.drinkcrafter.navigation.Routes

fun NavGraphBuilder.categoriesScreen(onCategoryClick: (String) -> Unit) {
    composable(
        route = Routes.Categories.route
    ) {
        CategoriesScreen(onCategoryClick)
    }
}