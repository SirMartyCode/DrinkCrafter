package com.sirmarty.drinkcrafter.ui.screens.categories

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sirmarty.drinkcrafter.ui.navigation.Routes

fun NavGraphBuilder.categoriesScreen(onCategoryClick: (String) -> Unit) {
    composable(
        route = Routes.Categories.route
    ) {
        CategoriesScreen(onCategoryClick)
    }
}