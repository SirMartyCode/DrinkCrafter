package com.sirmarty.drinkcrafter.ui.screens.drinklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkListUseCase
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel @Inject constructor(private val getDrinkListUseCase: GetDrinkListUseCase): ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<Drink>>>()
    val uiState: LiveData<UiState<List<Drink>>> = _uiState

    private var lastCategoryName: String = ""

    fun getDrinkList(categoryName: String) {
        if (lastCategoryName == categoryName ) {
            // We don't want to execute the use case if the category provided is exactly
            // the same we searched for the last time
            return
        }

        lastCategoryName = categoryName
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = getDrinkListUseCase.execute(categoryName)
                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }
}