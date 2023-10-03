package com.sirmarty.drinkcrafter.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.sirmarty.drinkcrafter.ui.screens.drinklist.DrinkList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onDrinkClick: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
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
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = query,
            onQueryChange = { viewModel.onQueryChanged(it) },
            onSearch = { keyboardController?.hide() },
            active = active,
            onActiveChange = { viewModel.onSearchActiveChanged(it) },
            placeholder = { Text(text = "Search cocktail by name") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "search icon"
                )
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "clear icon",
                        Modifier.clickable {
                            if (query.isEmpty()) {
                                viewModel.onSearchActiveChanged(false)
                            } else {
                                viewModel.onQueryChanged("")
                            }
                        }
                    )
                }
            }
        ) {
            DrinkList(drinks, onDrinkClick)
        }
    }
}