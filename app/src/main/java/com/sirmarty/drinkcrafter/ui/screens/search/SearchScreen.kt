package com.sirmarty.drinkcrafter.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.ui.screens.shared.DrinkList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onDrinkClick: (Int) -> Unit, viewModel: SearchViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val query: String by viewModel.query.observeAsState(initial = "")
    val active: Boolean by viewModel.isSearchActive.observeAsState(initial = false)
    val drinks: List<Drink> by viewModel.drinks.observeAsState(initial = emptyList())

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchBar(modifier = Modifier.fillMaxWidth(),
            query = query,
            onQueryChange = { viewModel.onQueryChanged(it) },
            onSearch = { keyboardController?.hide() },
            active = active,
            onActiveChange = { viewModel.onSearchActiveChanged(it) },
            placeholder = { Text(text = "Search cocktail by name") },
            leadingIcon = { SearchBarLeadingIcon(active) { viewModel.onSearchActiveChanged(false) } },
            trailingIcon = {
                SearchBarTrailingIcon(
                    active,
                    query
                ) { viewModel.clearSearch() }
            }) {
            DrinkList(drinks, onDrinkClick)
        }
    }
}

@Composable
fun SearchBarLeadingIcon(isSearchBarActive: Boolean, onBackIconClicked: () -> Unit) {
    return if (isSearchBarActive) {
        IconButton(onClick = { onBackIconClicked() }) {
            Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "back icon")
        }
    } else {
        Icon(
            imageVector = Icons.Outlined.Search, contentDescription = "search icon"
        )
    }
}

@Composable
fun SearchBarTrailingIcon(
    isSearchBarActive: Boolean, query: String, onTrailingIconClicked: () -> Unit
) {
    if (isSearchBarActive && query.isNotEmpty()) {
        IconButton(onClick = { onTrailingIconClicked() }) {
            Icon(imageVector = Icons.Outlined.Clear,
                contentDescription = "clear icon")
        }
    }
}