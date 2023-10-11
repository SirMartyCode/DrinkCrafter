package com.sirmarty.drinkcrafter.ui.screens.drinklist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
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
    val uiState by viewModel.uiState.observeAsState()

    // Avoid making the request each time the screen is recomposed
    LaunchedEffect(key1 = Unit) {
        viewModel.getDrinkList(categoryName)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when(uiState) {
            is DrinkListUiState.Error -> Text(text = (uiState as DrinkListUiState.Error).throwable.message ?: "ERROR")
            DrinkListUiState.Loading, null -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            is DrinkListUiState.Success -> DrinkList((uiState as DrinkListUiState.Success).drinkList, onDrinkClick)
        }
    }
}