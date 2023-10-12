package com.sirmarty.drinkcrafter.ui.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.usecase.SearchDrinkByNameUseCase
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDrinkByNameUseCase: SearchDrinkByNameUseCase
): ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _isSearchActive = MutableLiveData<Boolean>()
    val isSearchActive: LiveData<Boolean> = _isSearchActive

    private val _uiState = MutableLiveData<UiState<List<Drink>>>()
    val uiState: LiveData<UiState<List<Drink>>> = _uiState

    fun onSearchActiveChanged(active: Boolean) {
        _isSearchActive.value = active

        if(!active) {
            clearSearch()
        }
    }

    fun onQueryChanged(text: String) {
        _query.value = text
        search(text)
    }

    fun clearSearch() {
        onQueryChanged("")
    }

    private fun search(text: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = searchDrinkByNameUseCase.searchDrinkByName(text)
                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }
}