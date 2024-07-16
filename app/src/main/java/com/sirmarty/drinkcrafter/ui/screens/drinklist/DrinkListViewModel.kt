package com.sirmarty.drinkcrafter.ui.screens.drinklist

import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkListUseCase
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorViewModel
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel @Inject constructor(
    private val getDrinkListUseCase: GetDrinkListUseCase
): ErrorViewModel<List<Drink>>() {

    // Aux variable to remember last request
    private var lastCategoryName: String = ""

    fun getDrinkList(categoryName: String) {
        if (lastCategoryName == categoryName ) {
            // We don't want to execute the use case if the category provided is exactly
            // the same we searched for the last time
            return
        }

        lastCategoryName = categoryName

        viewModelScope.launch {
            mutableUiState.value = UiState.Loading
            try {
                val response = getDrinkListUseCase.execute(categoryName)
                mutableUiState.value = UiState.Success(response)
            } catch (e: Exception) {
                manageErrors(e)
            }
        }
    }

    //==============================================================================================
    //region ErrorViewModel methods

    override fun retryRequest() {
        // Reset lastCategoryName to force making the request again
        val categoryName = lastCategoryName
        lastCategoryName = ""
        getDrinkList(categoryName)
    }

    //endregion
}