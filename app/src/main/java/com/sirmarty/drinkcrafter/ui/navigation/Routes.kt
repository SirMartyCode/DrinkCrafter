package com.sirmarty.drinkcrafter.ui.navigation

sealed class Routes(val route: String) {
    object Search: Routes("search")
    object Home: Routes("home")
    object Categories: Routes("categories")
    object DrinkList: Routes("drinkList/{categoryName}") {
        const val categoryNameArg = "categoryName"
        fun createRoute(categoryName: String) = "drinkList/$categoryName"
    }
    object DrinkDetail: Routes("drinkDetail/{drinkId}") {
        const val drinkIdArg = "drinkId"
        fun createRoute(drinkId: Int) = "drinkDetail/$drinkId"
    }
}
