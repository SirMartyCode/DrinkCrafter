package com.sirmarty.drinkcrafter.ui.screens.ingredientdetail

import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.entity.IngredientDetail
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkListByIngredientUseCase
import com.sirmarty.drinkcrafter.domain.usecase.GetIngredientDetailUseCase
import com.sirmarty.drinkcrafter.ui.components.customtopappbar.CustomTopAppBarState
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorViewModel
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientDetailViewModel @Inject constructor(
    private val getIngredientDetailUseCase: GetIngredientDetailUseCase,
    private val getDrinkListByIngredientUseCase: GetDrinkListByIngredientUseCase
): ErrorViewModel<Pair<IngredientDetail, List<Drink>>>() {

    // TopBar state management
    private var _topAppBarState = MutableStateFlow(CustomTopAppBarState.SOLID)
    var topAppBarState = _topAppBarState.asStateFlow()

    private val _appBarHeight = MutableStateFlow<Float?>(null)
    private val _titleBottomOffset = MutableStateFlow<Float?>(null)

    // Aux variable to remember last request
    private var lastIngredient: String = ""

    fun getData(ingredient: String) {
        if (lastIngredient == ingredient) {
            // We don't want to execute the use case if the ingredient provided is exactly
            // the same we searched for the last time
            return
        }

        lastIngredient = ingredient

        viewModelScope.launch {
            mutableUiState.value = UiState.Loading
            try {
                // Wait for both use cases to be executed before returning a success state
                val ingredientDetailResponse = getIngredientDetailUseCase.execute(ingredient)
                val drinkListResponse = getDrinkListByIngredientUseCase.execute(ingredient)
                mutableUiState.value = UiState.Success(Pair(ingredientDetailResponse, drinkListResponse))
            } catch (e: Exception) {
                manageErrors(e)
            }
        }
    }

    fun updateAppBarHeight(height: Float) {
        _appBarHeight.value = height
        updateTopBarState()
    }

    fun updateTitleBottomOffset(offset: Float) {
        _titleBottomOffset.value = offset
        updateTopBarState()
    }

    //==============================================================================================
    //region Private methods

    private fun updateTopBarState() {
        if (_titleBottomOffset.value == null || _appBarHeight.value == null) {
            _topAppBarState.value = CustomTopAppBarState.SOLID
            return
        }

        if (_titleBottomOffset.value!! <= _appBarHeight.value!!) {
            _topAppBarState.value = CustomTopAppBarState.SOLID_WITH_TITLE
        } else {
            _topAppBarState.value = CustomTopAppBarState.SOLID
        }
    }

    //endregion
    //==============================================================================================
    //region ErrorViewModel methods

    override fun retryRequest() {
        // Reset lastIngredient to force making the request again
        val ingredient = lastIngredient
        lastIngredient = ""
        getData(ingredient)
    }

    //endregion
}