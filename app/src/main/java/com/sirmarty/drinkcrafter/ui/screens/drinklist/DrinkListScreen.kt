package com.sirmarty.drinkcrafter.ui.screens.drinklist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.ui.screens.shared.DrinkList

@Composable
fun DrinkListScreen(
    categoryName: String,
    onDrinkClick: (Int) -> Unit,
    viewModel: DrinkListViewModel = hiltViewModel(),
) {
    viewModel.getDrinkList(categoryName)

    val drinks by viewModel.drinks.observeAsState(initial = emptyList())

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        DrinkList(drinks, onDrinkClick)
    }
}