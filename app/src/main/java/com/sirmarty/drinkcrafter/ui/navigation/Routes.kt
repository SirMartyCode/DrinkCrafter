package com.sirmarty.drinkcrafter.ui.navigation

sealed class Routes(val route: String) {
    object Home: Routes("home")
    object Explore: Routes("explore")
    object Search: Routes("search")
    object Saved: Routes("saved")
    object Categories: Routes("categories")
    object DrinkList: Routes("drinkList/{categoryName}") {
        const val categoryNameArg = "categoryName"
        /**
         * Since categoryNameArg can contain a slash ("/") we have to replace that character with
         * a hyphen to prevent the NavController from interpreting it as a navigation feature
         */
        fun createRoute(categoryName: String): String {
            return "drinkList/${categoryName.replace("/", "-")}"
        }
        /**
         * Then we must replace it back to get the original category name. Otherwise, the WS
         * would not recognize the name when we request the drinks belonging to this category
         */
        fun getArgumentValue(argument: String?) = argument?.replace("-", "/")
    }
    object DrinkDetail: Routes("drinkDetail/{drinkId}") {
        const val drinkIdArg = "drinkId"
        fun createRoute(drinkId: Int) = "drinkDetail/$drinkId"
    }
    object Ingredient: Routes("ingredient/{ingredientName}") {
        const val ingredientNameArg = "ingredientName"
        fun createRoute(ingredient: String) = "ingredient/$ingredient"
    }
}
