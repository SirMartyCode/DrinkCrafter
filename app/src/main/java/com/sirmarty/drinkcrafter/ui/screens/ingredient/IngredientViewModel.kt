package com.sirmarty.drinkcrafter.ui.screens.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkListByIngredientUseCase
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(
    private val getDrinkListByIngredientUseCase: GetDrinkListByIngredientUseCase
): ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<Drink>>>()
    val uiState: LiveData<UiState<List<Drink>>> = _uiState

    fun getDrinkList(ingredient: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = getDrinkListByIngredientUseCase.execute(ingredient)
                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }

}