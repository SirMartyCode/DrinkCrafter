package com.sirmarty.drinkcrafter.ui.screens.categorydetail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.ui.components.customloading.CustomLoading
import com.sirmarty.drinkcrafter.ui.components.drinklist.DrinkList
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorLayout
import com.sirmarty.drinkcrafter.ui.components.screentitle.ScreenTitle
import com.sirmarty.drinkcrafter.ui.screens.UiState

@Composable
fun CategoryDetailScreen(
    categoryName: String,
    onDrinkClick: (Int) -> Unit,
    viewModel: CategoryDetailViewModel = hiltViewModel(),
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
            .background(MaterialTheme.colorScheme.background)
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
                CustomLoading(Modifier.align(Alignment.Center))
            }

            is UiState.Success -> {
                CategoryDetailLayout(
                    categoryName = categoryName,
                    context = context,
                    drinks = (uiState as UiState.Success).value,
                    onDrinkClick = onDrinkClick
                )
            }
        }
    }
}


//==================================================================================================
//region Private composable

@Composable
private fun CategoryDetailLayout(
    categoryName: String,
    context: Context,
    drinks: List<Drink>,
    onDrinkClick: (Int) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        ScreenTitle(
            text = categoryName,
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
        )
        DrinkList(context, drinks, onDrinkClick)
    }
}

//endregion