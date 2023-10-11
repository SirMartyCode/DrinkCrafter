package com.sirmarty.drinkcrafter.ui.screens.drinklist

import com.sirmarty.drinkcrafter.domain.entity.Drink

sealed interface DrinkListUiState {
    object Loading: DrinkListUiState
    data class Error(val throwable: Throwable): DrinkListUiState
    data class Success(val drinkList: List<Drink>): DrinkListUiState
}