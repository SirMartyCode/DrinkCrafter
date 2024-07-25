package com.sirmarty.drinkcrafter.ui.screens.ingredientlist

import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.IngredientName
import com.sirmarty.drinkcrafter.domain.usecase.GetIngredientListUseCase
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorViewModel
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientListViewModel @Inject constructor(
    private val getIngredientListUseCase: GetIngredientListUseCase
): ErrorViewModel<List<IngredientName>>() {

    init {
        getData()
    }

    //==============================================================================================
    //region Private methods

    private fun getData() {
        viewModelScope.launch {
            mutableUiState.value = UiState.Loading
            try {
                val response = getIngredientListUseCase.execute()
                mutableUiState.value = UiState.Success(response)
            } catch (e: Exception) {
                manageErrors(e)
            }

        }
    }

    //endregion
    //==============================================================================================
    //region ErrorViewModel methods

    override fun retryRequest() {
        getData()
    }

    //endregion
}