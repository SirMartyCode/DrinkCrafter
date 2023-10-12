package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailViewModel @Inject constructor(private val getDrinkDetailUseCase: GetDrinkDetailUseCase) : ViewModel() {

    private val _uiState = MutableLiveData<DrinkDetailUiState>()
    val uiState: LiveData<DrinkDetailUiState> = _uiState

    fun getDrinkDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = DrinkDetailUiState.Loading
            try {
                val response = getDrinkDetailUseCase.execute(id)
                _uiState.value = DrinkDetailUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = DrinkDetailUiState.Error(e)
            }
        }
    }
}