package com.sirmarty.drinkcrafter.ui.screens.categories

import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.usecase.GetCategoryListUseCase
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorViewModel
import com.sirmarty.drinkcrafter.ui.model.CategoryWithImage
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val categoryImageMapper: CategoryImageMapper
): ErrorViewModel<List<CategoryWithImage>>() {

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            mutableUiState.value = UiState.Loading
            try {
                val response = getCategoryListUseCase.execute()
                val categoriesWithImages = response.map { category ->
                    categoryImageMapper.getCategoryWithImageForCategoryName(category.name)
                }
                mutableUiState.value = UiState.Success(categoriesWithImages)
            } catch (e: Exception) {
                manageErrors(e)
            }
        }
    }

    //==============================================================================================
    //region ErrorViewModel methods

    override fun retryRequest() {
        getCategories()
    }

    //endregion
}