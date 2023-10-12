package com.sirmarty.drinkcrafter.ui.screens

sealed interface UiState<out T> {
    object Loading: UiState<Nothing>
    data class Error(val throwable: Throwable): UiState<Nothing>
    data class Success<T>(val value: T): UiState<T>
}