package com.sirmarty.drinkcrafter.ui.components.searchbar

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.sirmarty.drinkcrafter.R

/**
 * As a aesthetic decision, the SearchBar "active" parameter will always be set to false
 * Therefore there will be no need to handle the onActiveChanged callback
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    context: Context,
    query: String,
    placeholder: String,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
        placeholder = { Text(text = placeholder) },
        leadingIcon = {
            SearchBarLeadingIcon(context)
        },
        trailingIcon = {
            SearchBarTrailingIcon(context, query) {
                onTrailingIconClick()
            }
        }
    ) { }
}

//==================================================================================================
//region Private composable

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