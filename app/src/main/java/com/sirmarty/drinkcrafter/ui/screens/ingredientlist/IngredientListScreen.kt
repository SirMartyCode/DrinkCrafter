package com.sirmarty.drinkcrafter.ui.screens.ingredientlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.domain.entity.IngredientName
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorLayout
import com.sirmarty.drinkcrafter.ui.screens.UiState

@Composable
fun IngredientListScreen(
    onIngredientClick: (String) -> Unit,
    viewModel: IngredientListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Loading)
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()

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
                IngredientList((uiState as UiState.Success).value, onIngredientClick)
            }
        }
    }
}

@Composable
private fun IngredientList(
    ingredients: List<IngredientName>,
    onIngredientClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = ingredients
        ) {
            IngredientItem(it, onIngredientClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IngredientItem(
    ingredient: IngredientName,
    onIngredientClick: (String) -> Unit
) {
    ElevatedCard(
        onClick = { onIngredientClick(ingredient.name) },
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Text(
            text = ingredient.name,
        )
    }
}