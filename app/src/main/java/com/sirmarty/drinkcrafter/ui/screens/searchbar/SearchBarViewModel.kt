package com.sirmarty.drinkcrafter.ui.screens.searchbar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.usecase.SearchDrinkByNameUseCase
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorViewModel
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBarViewModel @Inject constructor(
    private val searchDrinkByNameUseCase: SearchDrinkByNameUseCase
): ErrorViewModel<List<Drink>>() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    fun onQueryChanged(text: String) {
        _query.value = text
        search(text)
    }

    fun clearSearch() {
        onQueryChanged("")
    }

    //==============================================================================================
    //region Private methods

    private fun search(text: String) {
        viewModelScope.launch {
            mutableUiState.value = UiState.Loading
            try {
                val response = searchDrinkByNameUseCase.searchDrinkByName(text)
                mutableUiState.value = UiState.Success(response)
            } catch (e: Exception) {
                manageErrors(e)
            }
        }
    }

    //endregion
    //==============================================================================================
    //region ErrorViewModel methods

    override fun retryRequest() {
        _query.value?.let { search(it) }
    }

    //endregion
}