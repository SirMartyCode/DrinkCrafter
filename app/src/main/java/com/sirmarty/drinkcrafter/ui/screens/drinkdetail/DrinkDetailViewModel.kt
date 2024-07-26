package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkDetailUseCase
import com.sirmarty.drinkcrafter.domain.usecase.GetRandomDrinkDetailUseCase
import com.sirmarty.drinkcrafter.ui.components.customtopappbar.CustomTopAppBarState
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorViewModel
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
) : ErrorViewModel<DrinkDetail>() {

    companion object {
        private const val ID_RANDOM_DRINK = -1
    }

    // TopBar state management
    private var _topAppBarState = MutableStateFlow(CustomTopAppBarState.TRANSPARENT)
    var topAppBarState = _topAppBarState.asStateFlow()

    private val _appBarHeight = MutableStateFlow<Float?>(null)
    private val _imageBottomOffset = MutableStateFlow<Float?>(null)
    private val _titleBottomOffset = MutableStateFlow<Float?>(null)

    // Aux variable to remember last request
    private var lastDrinkId: Int? = null

    fun getDrinkDetail(id: Int) {
        if (lastDrinkId == id) {
            // We don't want to execute the use case if the ingredient provided is exactly
            // the same we searched for the last time
            return
        }

        if (lastDrinkId != null && id == ID_RANDOM_DRINK) {
            // If lastDrinkId is not null and current id is equal to random drink id it means
            // we're coming back to drink detail from another screen, so we'll skip the request
            return
        }

        lastDrinkId = id

        viewModelScope.launch {
            mutableUiState.value = UiState.Loading
            try {
                val response = if (id == ID_RANDOM_DRINK) {
                    getRandomDrinkDetailUseCase.execute()
                } else {
                    getDrinkDetailUseCase.execute(id)
                }
                lastDrinkId = response.id
                mutableUiState.value = UiState.Success(response)
            } catch (e: Exception) {
                manageErrors(e)
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

    //==============================================================================================
    //region Private methods

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

    //endregion
    //==============================================================================================
    //region ErrorViewModel methods

    override fun retryRequest() {
        // Reset lastDrinkId to force making the request again
        val drinkId = lastDrinkId
        lastDrinkId = null
        drinkId?.let { getDrinkDetail(it) }
    }

    //endregion
}