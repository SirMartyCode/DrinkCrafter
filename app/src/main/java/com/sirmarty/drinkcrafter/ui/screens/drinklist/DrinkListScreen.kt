package com.sirmarty.drinkcrafter.ui.screens.drinklist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.ui.components.drinklist.DrinkList
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorLayout
import com.sirmarty.drinkcrafter.ui.screens.UiState

@Composable
fun DrinkListScreen(
    categoryName: String,
    onDrinkClick: (Int) -> Unit,
    viewModel: DrinkListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Loading)
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()

    val context = LocalContext.current

    // Avoid making the request each time the screen is recomposed
    LaunchedEffect(key1 = Unit) {
        viewModel.getDrinkList(categoryName)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (uiState) {
            is UiState.Error -> {
                ErrorLayout(
                    throwable = (uiState as UiState.Error).throwable,
                    showErrorDialog = showErrorDialog,
                    onDismissRequest = { viewModel.hideErrorDialog() },
                    onConfirmation = { viewModel.retryRequest() }
                )
            }

            UiState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

            is UiState.Success -> {
                DrinkList(context, drinks = (uiState as UiState.Success).value, onDrinkClick)
            }
        }
    }
}