package com.sirmarty.drinkcrafter.ui.screens.ingredient

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.ui.components.drinklist.DrinkList
import com.sirmarty.drinkcrafter.ui.screens.UiState

@Composable
fun IngredientScreen(
    ingredient: String,
    onBackClick: () -> Unit,
    viewModel: IngredientViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Loading)
    val context = LocalContext.current

    // Avoid making the request each time the screen is recomposed
    LaunchedEffect(key1 = Unit) {
        viewModel.getDrinkList(ingredient)
    }

    Scaffold { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                is UiState.Error -> {
                    Text(
                        text = (uiState as UiState.Error).throwable.message
                            ?: "UNKNOWN ERROR",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                UiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                is UiState.Success -> {
                    IngredientView(
                        context,
                        (uiState as UiState.Success).value,
                        onBackClick
                    )
                }
            }
        }
    }
}

// TODO: improve this a little bit and add the needed callbacks
@Composable
fun IngredientView(context: Context, drinks: List<Drink>, onBackClick: () -> Unit) {
    DrinkList(context, drinks , {})
}
