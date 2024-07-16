package com.sirmarty.drinkcrafter.ui.screens.searchbar

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.ui.components.drinklist.DrinkList
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorLayout
import com.sirmarty.drinkcrafter.ui.screens.UiState

@Composable
fun SearchBarScreen(
    onDrinkClick: (Int) -> Unit,
    viewModel: SearchBarViewModel = hiltViewModel()
) {
    val query: String by viewModel.query.observeAsState(initial = "")
    val uiState by viewModel.uiState.observeAsState()
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchBarLayout(
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

/**
 * As a aesthetic decision, the SearchBar "active" parameter will always be set to false
 * Therefore there will be no need to handle the onActiveChanged callback
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarLayout(
    uiState: UiState<List<Drink>>?,
    query: String,
    showErrorDialog: Boolean,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    onDrinkClick: (Int) -> Unit,
    onHideErrorDialog: () -> Unit,
    onRetryRequest: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(
        Modifier.fillMaxSize()
    ) {
        DockedSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            query = query,
            onQueryChange = { onQueryChange(it) },
            onSearch = { keyboardController?.hide() },
            active = false,
            onActiveChange = {
                // Nothing to do
            },
            placeholder = { Text(text = context.getString(R.string.search_bar_hint)) },
            leadingIcon = {
                SearchBarLeadingIcon(context)
            },
            trailingIcon = {
                SearchBarTrailingIcon(context, query) {
                    onTrailingIconClick()
                }
            }
        ) { }

        HorizontalDivider(Modifier.fillMaxWidth())

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
                    CircularProgressIndicator()
                }

                is UiState.Success -> {
                    SearchResult(context, uiState.value, query, onDrinkClick)
                }

                else -> {
                    // Show nothing when user hasn't already searched
                }
            }
        }
    }
}

@Composable
private fun SearchResult(
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

@Composable
private fun SearchBarLeadingIcon(context: Context) {
    Icon(
        imageVector = Icons.Outlined.Search,
        contentDescription = context.getString(R.string.search_bar_search)
    )
}

@Composable
private fun SearchBarTrailingIcon(
    context: Context, query: String, onClick: () -> Unit
) {
    if (query.isNotEmpty()) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = context.getString(R.string.search_bar_clear)
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
            .background(Color.White)
    ) {
        SearchBarLayout(
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