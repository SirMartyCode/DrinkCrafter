package com.sirmarty.drinkcrafter.ui.screens.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.entity.IngredientDetail
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkListByIngredientUseCase
import com.sirmarty.drinkcrafter.domain.usecase.GetIngredientDetailUseCase
import com.sirmarty.drinkcrafter.ui.components.customtopappbar.CustomTopAppBarState
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(
    private val getIngredientDetailUseCase: GetIngredientDetailUseCase,
    private val getDrinkListByIngredientUseCase: GetDrinkListByIngredientUseCase
): ViewModel() {

    private val _uiState = MutableLiveData<UiState<Pair<IngredientDetail, List<Drink>>>>()
    val uiState: LiveData<UiState<Pair<IngredientDetail, List<Drink>>>> = _uiState

    private var lastIngredient: String = ""

    private var _topAppBarState = MutableStateFlow(CustomTopAppBarState.SOLID)
    var topAppBarState = _topAppBarState.asStateFlow()

    private val _appBarHeight = MutableStateFlow<Float?>(null)
    private val _titleBottomOffset = MutableStateFlow<Float?>(null)

    fun getData(ingredient: String) {
        if (lastIngredient == ingredient) {
            // We don't want to execute the use case if the ingredient provided is exactly
            // the same we searched for the last time
            return
        }

        lastIngredient = ingredient

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                // Wait for both use cases to be executed before returning a success state
                val ingredientDetailResponse = getIngredientDetailUseCase.execute(ingredient)
                val drinkListResponse = getDrinkListByIngredientUseCase.execute(ingredient)
                _uiState.value = UiState.Success(Pair(ingredientDetailResponse, drinkListResponse))
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
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

}