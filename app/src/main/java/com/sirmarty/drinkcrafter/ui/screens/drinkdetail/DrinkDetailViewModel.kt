package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkDetailUseCase
import com.sirmarty.drinkcrafter.domain.usecase.GetRandomDrinkDetailUseCase
import com.sirmarty.drinkcrafter.ui.components.customtopappbar.CustomTopAppBarState
import com.sirmarty.drinkcrafter.ui.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private var _topAppBarState = MutableStateFlow(CustomTopAppBarState.SOLID)
    var topAppBarState = _topAppBarState.asStateFlow()

    private val _appBarHeight = MutableStateFlow<Float?>(null)
    private val _imageBottomOffset = MutableStateFlow<Float?>(null)
    private val _titleBottomOffset = MutableStateFlow<Float?>(null)

    fun getDrinkDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = if (id == ID_RANDOM_DRINK) {
                    getRandomDrinkDetailUseCase.execute()
                } else {
                    getDrinkDetailUseCase.execute(id)
                }
                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e)
            }
        }
    }

    fun updateAppBarHeight(height: Float) {
        _appBarHeight.value = height
        updateTopBarState()
    }

    fun updateImageBottomOffset(offset: Float) {
        _imageBottomOffset.value = offset
        updateTopBarState()
    }

    fun updateTitleBottomOffset(offset: Float) {
        _titleBottomOffset.value = offset
        updateTopBarState()
    }

    private fun updateTopBarState() {
        if (_imageBottomOffset.value == null || _titleBottomOffset.value == null || _appBarHeight.value == null) {
            _topAppBarState.value = CustomTopAppBarState.TRANSPARENT
            return
        }

        if (_imageBottomOffset.value!! <= _appBarHeight.value!!) {
            if (_titleBottomOffset.value!! <= _appBarHeight.value!!) {
                _topAppBarState.value = CustomTopAppBarState.SOLID_WITH_TITLE
            } else {
                _topAppBarState.value = CustomTopAppBarState.SOLID
            }
        } else {
            _topAppBarState.value = CustomTopAppBarState.TRANSPARENT
        }
    }
}