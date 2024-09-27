package com.sirmarty.drinkcrafter.ui.screens.drinksearch

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.ui.components.customloading.CustomLoading
import com.sirmarty.drinkcrafter.ui.components.drinklist.DrinkList
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorLayout
import com.sirmarty.drinkcrafter.ui.components.searchbar.CustomSearchBar
import com.sirmarty.drinkcrafter.ui.screens.UiState

@Composable
fun DrinkSearchScreen(
    onDrinkClick: (Int) -> Unit,
    viewModel: DrinkSearchViewModel = hiltViewModel()
) {
    val query: String by viewModel.query.observeAsState(initial = "")
    val uiState by viewModel.uiState.observeAsState()
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DrinkSearchLayout(
            uiState = uiState,
            query = query,
            showErrorDialog = showErrorDialog,
            onQueryChange = { viewModel.onQueryChanged(it) },
            onTrailingIconClick = { viewModel.clearSearch() },
            onDrinkClick = onDrinkClick,
            onHideErrorDialog = { viewModel.hideErrorDialog() },
            onRetryRequest = { viewModel.retryRequest() }
        )
    }
}

//==================================================================================================
//region Private composable

@Composable
fun DrinkSearchLayout(
    uiState: UiState<List<Drink>>?,
    query: String,
    showErrorDialog: Boolean,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    onDrinkClick: (Int) -> Unit,
    onHideErrorDialog: () -> Unit,
    onRetryRequest: () -> Unit
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
                    DrinkSearchResult(context, uiState.value, query, onDrinkClick)
                }

                else -> {
                    // Show nothing when user hasn't already searched
                }
            }
        }
    }
}

@Composable
private fun DrinkSearchResult(
    context: Context,
    drinkList: List<Drink>,
    query: String,
    onDrinkClick: (Int) -> Unit
) {
    // empty query = no search
    if (query.isNotEmpty()) {
        if (drinkList.isNotEmpty()) {
            // query + search result = drink list
            DrinkList(
                context,
                drinks = drinkList,
                onDrinkClick
            )
        } else {
            // query + empty search result = no coincidences
            Text(
                text = context.getString(R.string.search_bar_empty_result),
            )
        }
    }
}

//endregion
//==================================================================================================
//region Preview

@Preview
@Composable
private fun SearchBarScreenPreview() {

    val drinks = listOf(
        Drink(1, "Drink1", "Image1"),
        Drink(2, "Drink2", "Image2"),
        Drink(3, "Drink3", "Image3"),
        Drink(4, "Drink4", "Image4"),
    )
    val uiState = UiState.Success(drinks)

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DrinkSearchLayout(
            uiState = uiState,
            query = "",
            showErrorDialog = false,
            onQueryChange = {},
            onTrailingIconClick = {},
            onDrinkClick = {},
            onHideErrorDialog = {},
            onRetryRequest = {}
        )
    }
}

//endregion