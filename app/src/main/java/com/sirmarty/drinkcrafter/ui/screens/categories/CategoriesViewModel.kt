package com.sirmarty.drinkcrafter.ui.screens.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.usecase.GetCategoryListUseCase
import com.sirmarty.drinkcrafter.ui.model.CategoryWithImage
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val categoryImageMapper: CategoryImageMapper
): ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<CategoryWithImage>>>()
    val uiState: LiveData<UiState<List<CategoryWithImage>>> = _uiState

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = getCategoryListUseCase.execute()
                val categoriesWithImages = response.map { category ->
                    categoryImageMapper.getCategoryWithImageForCategoryName(category.name)
                }
                _uiState.value = UiState.Success(categoriesWithImages)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }
}