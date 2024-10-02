package com.sirmarty.drinkcrafter.ui.screens.saved

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.ui.components.customloading.CustomLoading
import com.sirmarty.drinkcrafter.ui.components.drinklist.DrinkList
import com.sirmarty.drinkcrafter.ui.components.screentitle.ScreenTitle
import com.sirmarty.drinkcrafter.ui.screens.UiState

@Composable
fun SavedScreen(
    onDrinkClick: (Int) -> Unit,
    viewModel: SavedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<UiState<List<Drink>>>(
        initialValue = UiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                CustomLoading(Modifier.align(Alignment.Center))
            }

            is UiState.Success -> {
                SavedLayout(
                    drinks = (uiState as UiState.Success).value,
                    context = context,
                    onDrinkClick = onDrinkClick
                )
            }
        }
    }
}


//==================================================================================================
//region Private composable

@Composable
private fun SavedLayout(
    drinks: List<Drink>,
    context: Context,
    onDrinkClick: (Int) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        ScreenTitle(
            stringRes = R.string.saved_title,
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
        )
        if (drinks.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = context.getString(R.string.saved_no_saved_drinks),
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            DrinkList(context, drinks = drinks, onDrinkClick)
        }
    }
}

//endregion