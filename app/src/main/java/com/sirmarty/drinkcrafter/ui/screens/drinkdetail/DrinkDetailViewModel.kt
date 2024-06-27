package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkDetailUseCase
import com.sirmarty.drinkcrafter.domain.usecase.GetRandomDrinkDetailUseCase
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailViewModel @Inject constructor(
    private val getDrinkDetailUseCase: GetDrinkDetailUseCase,
    private val getRandomDrinkDetailUseCase: GetRandomDrinkDetailUseCase
) : ViewModel() {

    companion object {
        private const val ID_RANDOM_DRINK = -1
    }

    private val _uiState = MutableLiveData<UiState<DrinkDetail>>()
    val uiState: LiveData<UiState<DrinkDetail>> = _uiState

    fun getDrinkDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response =
                    if (id == ID_RANDOM_DRINK) getRandomDrinkDetail() else getDrinkDetailWithId(id)
                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }

    private suspend fun getDrinkDetailWithId(id: Int): DrinkDetail {
        return getDrinkDetailUseCase.execute(id)
    }

    private suspend fun getRandomDrinkDetail(): DrinkDetail {
        return getRandomDrinkDetailUseCase.execute()
    }
}