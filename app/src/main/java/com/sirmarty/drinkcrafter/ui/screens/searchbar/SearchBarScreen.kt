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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.sirmarty.drinkcrafter.ui.screens.UiState

@Composable
fun SearchBarScreen(
    onDrinkClick: (Int) -> Unit,
    viewModel: SearchBarViewModel = hiltViewModel()
) {
    val query: String by viewModel.query.observeAsState(initial = "")
    val uiState by viewModel.uiState.observeAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchBarLayout(
            uiState,
            query,
            onQueryChange = { viewModel.onQueryChanged(it) },
            onTrailingIconClick = { viewModel.clearSearch() }
        ) { onDrinkClick(it) }
    }
}

/**
 * As a aesthetic decision, the SearchBar "active" parameter will always be set to false
 * Therefore there will be no need to handle the onActiveChanged callback
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarLayout(
    uiState: UiState<List<Drink>>?,
    query: String,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    onDrinkClick: (Int) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(
        Modifier.fillMaxSize()
    ) {
        SearchBar(
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
            placeholder = { Text(text = context.getString(R.string.search_search_hint)) },
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

        Box(Modifier.fillMaxSize()) {
            when (uiState) {
                is UiState.Error -> {
                    Text(
                        text = uiState.throwable.message ?: "UNKNOWN ERROR",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                UiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                is UiState.Success -> {
                    // empty query = no search
                    if(query.isNotEmpty()) {
                        if (uiState.value.isNotEmpty()) {
                            // query + search result = drink list
                            DrinkList(
                                context,
                                drinks = uiState.value,
                                onDrinkClick
                            )
                        } else {
                            // query + empty search result = no coincidences
                            Text(
                                text = context.getString(R.string.search_empty_result),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

                else -> {
                    // Show nothing when user hasn't already searched
                }
            }
        }
    }
}

@Composable
fun SearchBarLeadingIcon(context: Context) {
    Icon(
        imageVector = Icons.Outlined.Search,
        contentDescription = context.getString(R.string.search_search)
    )
}

@Composable
fun SearchBarTrailingIcon(
    context: Context, query: String, onClick: () -> Unit
) {
    if (query.isNotEmpty()) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = context.getString(R.string.search_clear)
            )
        }
    }
}

@Preview
@Composable
fun SearchBarScreenPreview() {

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
            uiState,
            "",
            onQueryChange = {},
            onTrailingIconClick = {}
        ) {}
    }
}
