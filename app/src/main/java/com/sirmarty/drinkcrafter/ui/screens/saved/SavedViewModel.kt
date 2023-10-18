package com.sirmarty.drinkcrafter.ui.screens.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.usecase.GetSavedDrinksListUseCase
import com.sirmarty.drinkcrafter.ui.screens.UiState
import com.sirmarty.drinkcrafter.ui.screens.UiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    getSavedDrinksListUseCase: GetSavedDrinksListUseCase
) : ViewModel() {

    val uiState: StateFlow<UiState<List<Drink>>> = getSavedDrinksListUseCase.execute().map(::Success)
        .catch { UiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)
}