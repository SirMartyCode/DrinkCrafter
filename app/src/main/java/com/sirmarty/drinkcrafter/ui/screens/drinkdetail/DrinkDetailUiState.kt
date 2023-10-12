package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail

sealed interface DrinkDetailUiState {
    object Loading: DrinkDetailUiState
    data class Error(val throwable: Throwable): DrinkDetailUiState
    data class Success(val drinkDetail: DrinkDetail): DrinkDetailUiState
}