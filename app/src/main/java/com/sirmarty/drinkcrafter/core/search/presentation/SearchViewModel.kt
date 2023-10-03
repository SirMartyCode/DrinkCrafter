package com.sirmarty.drinkcrafter.core.search.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.core.drink.domain.entity.Drink
import com.sirmarty.drinkcrafter.core.search.domain.usecase.SearchDrinkByNameUseCase
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

    private val _drinks = MutableLiveData<List<Drink>>()
    val drinks: LiveData<List<Drink>> = _drinks

    fun onSearchActiveChanged(active: Boolean) {
        _isSearchActive.value = active
    }

    fun onQueryChanged(text: String) {
        _query.value = text
        if (text.isNotEmpty()) {
            search(text)
        }
    }

    fun search(text: String) {
        viewModelScope.launch {
            try {
                val response = searchDrinkByNameUseCase.searchDrinkByName(text)
                _drinks.value = response
            } catch (e: Exception) {
                Log.i("SirMarty:", "ERROR! - Search Drink (not implemented yet)")
            }
        }
    }
}