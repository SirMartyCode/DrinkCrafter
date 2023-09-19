package com.sirmarty.drinkcrafter.categories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.categories.domain.entity.Category
import com.sirmarty.drinkcrafter.categories.domain.usecase.GetCategoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val getCategoryListUseCase: GetCategoryListUseCase): ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    fun getCategories() {
        viewModelScope.launch {
            _categories.value = getCategoryListUseCase.execute()
        }
    }
}