package com.sirmarty.drinkcrafter.ui.screens.drinklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel @Inject constructor(private val getDrinkListUseCase: GetDrinkListUseCase): ViewModel() {

    private val _uiState = MutableLiveData<DrinkListUiState>()
    val uiState: LiveData<DrinkListUiState> = _uiState

    fun getDrinkList(categoryName: String) {
        viewModelScope.launch {
            _uiState.value = DrinkListUiState.Loading
            try {
                val response = getDrinkListUseCase.execute(categoryName)
                _uiState.value = DrinkListUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = DrinkListUiState.Error(e)
            }
        }
    }
}