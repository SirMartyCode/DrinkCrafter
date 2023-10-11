package com.sirmarty.drinkcrafter.ui.screens.categories

import com.sirmarty.drinkcrafter.domain.entity.Category

sealed interface CategoriesUiState {
    object Loading: CategoriesUiState
    data class Error(val throwable: Throwable): CategoriesUiState
    data class Success(val categories: List<Category>): CategoriesUiState
}