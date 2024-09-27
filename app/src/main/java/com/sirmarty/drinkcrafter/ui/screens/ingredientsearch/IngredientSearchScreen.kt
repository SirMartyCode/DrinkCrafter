package com.sirmarty.drinkcrafter.ui.screens.ingredientsearch

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.domain.entity.IngredientName
import com.sirmarty.drinkcrafter.ui.components.cardwithouttonalelevation.CardWithoutTonalElevation
import com.sirmarty.drinkcrafter.ui.components.customloading.CustomLoading
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorLayout
import com.sirmarty.drinkcrafter.ui.components.searchbar.CustomSearchBar
import com.sirmarty.drinkcrafter.ui.screens.UiState

@Composable
fun IngredientSearchScreen(
    onIngredientClick: (String) -> Unit,
    viewModel: IngredientSearchViewModel = hiltViewModel()
) {
    val query by viewModel.query.observeAsState(initial = "")
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Loading)
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        IngredientSearchLayout(
            uiState = uiState,
            query = query,
            showErrorDialog = showErrorDialog,
            onQueryChange = { viewModel.onQueryChanged(it) },
            onTrailingIconClick = { viewModel.clearSearch() },
            onHideErrorDialog = { viewModel.hideErrorDialog() },
            onRetryRequest = { viewModel.retryRequest() },
            onIngredientClick = onIngredientClick
        )
    }
}


//==================================================================================================
//region Private composable

@Composable
private fun IngredientSearchLayout(
    uiState: UiState<List<IngredientName>>,
    query: String,
    showErrorDialog: Boolean,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    onHideErrorDialog: () -> Unit,
    onRetryRequest: () -> Unit,
    onIngredientClick: (String) -> Unit
) {

    val context = LocalContext.current

    Column(
        Modifier.fillMaxSize()
    ) {

        CustomSearchBar(
            context = context,
            query = query,
            placeholder = context.getString(R.string.search_bar_hint),
            onQueryChange = onQueryChange,
            onTrailingIconClick = onTrailingIconClick
        )

        HorizontalDivider(
            Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is UiState.Error -> {
                    ErrorLayout(
                        throwable = uiState.throwable,
                        showErrorDialog = showErrorDialog,
                        onDismissRequest = onHideErrorDialog,
                        onConfirmation = onRetryRequest
                    )
                }

                UiState.Loading -> {
                    CustomLoading()
                }

                is UiState.Success -> {
                    IngredientSearchResult(
                        context = context,
                        query = query,
                        ingredients = uiState.value,
                        onIngredientClick = onIngredientClick
                    )
                }

            }
        }
    }
}

@Composable
private fun IngredientSearchResult(
    context: Context,
    query: String,
    ingredients: List<IngredientName>,
    onIngredientClick: (String) -> Unit
) {
    if (ingredients.isNotEmpty()) {
        IngredientList(ingredients, onIngredientClick)
    } else {
        if (query.isNotEmpty()) {
            Text(text = context.getString(R.string.search_bar_empty_result))
        } else {
            // This case will never happen
        }
    }
}

@Composable
private fun IngredientList(
    ingredients: List<IngredientName>,
    onIngredientClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
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

@Composable
private fun IngredientItem(
    ingredient: IngredientName,
    onIngredientClick: (String) -> Unit
) {
    CardWithoutTonalElevation(
        onClick = { onIngredientClick(ingredient.name) },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = ingredient.name,
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

//endregion
//==================================================================================================
//region Preview

@Preview
@Composable
fun IngredientListScreenPreview() {

    val ingredients = listOf(
        IngredientName("Ingredient1"),
        IngredientName("Ingredient2"),
        IngredientName("Ingredient3"),
        IngredientName("Ingredient4"),
        IngredientName("Ingredient5"),
        IngredientName("Ingredient6"),
        IngredientName("Ingredient7"),
        IngredientName("Ingredient8"),
        IngredientName("Ingredient9")
    )
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        IngredientList(ingredients = ingredients, onIngredientClick = {})
    }
}

//endregion