package com.sirmarty.drinkcrafter.ui.screens.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.Category
import com.sirmarty.drinkcrafter.domain.usecase.GetCategoryListUseCase
import com.sirmarty.drinkcrafter.ui.model.CategoryEnum
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val getCategoryListUseCase: GetCategoryListUseCase): ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<CategoryEnum>>>()
    val uiState: LiveData<UiState<List<CategoryEnum>>> = _uiState

    init {
        _uiState.value = UiState.Success(CategoryEnum.values().toList())
        //getCategories()
    }

    private fun getCategories() {
        /*
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = getCategoryListUseCase.execute()
                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
            }
        }

         */
    }
}