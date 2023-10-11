package com.sirmarty.drinkcrafter.ui.screens.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.usecase.GetCategoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val getCategoryListUseCase: GetCategoryListUseCase): ViewModel() {

    private val _uiState = MutableLiveData<CategoriesUiState>()
    val uiState: LiveData<CategoriesUiState> = _uiState

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _uiState.value = CategoriesUiState.Loading
            try {
                val response = getCategoryListUseCase.execute()
                _uiState.value = CategoriesUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = CategoriesUiState.Error(e)
            }
        }
    }
}