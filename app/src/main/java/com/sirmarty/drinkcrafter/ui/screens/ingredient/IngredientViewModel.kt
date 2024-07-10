package com.sirmarty.drinkcrafter.ui.screens.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.entity.IngredientDetail
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkListByIngredientUseCase
import com.sirmarty.drinkcrafter.domain.usecase.GetIngredientDetailUseCase
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
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

}