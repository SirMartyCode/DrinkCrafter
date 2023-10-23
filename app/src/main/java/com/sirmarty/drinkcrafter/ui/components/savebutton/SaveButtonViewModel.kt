package com.sirmarty.drinkcrafter.ui.components.savebutton

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.domain.usecase.DeleteDrinkDetailUseCase
import com.sirmarty.drinkcrafter.domain.usecase.IsSavedUseCase
import com.sirmarty.drinkcrafter.domain.usecase.SaveDrinkDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveButtonViewModel @Inject constructor(
    private val isSavedUseCase: IsSavedUseCase,
    private val saveDrinkDetailUseCase: SaveDrinkDetailUseCase,
    private val deleteDrinkDetailUseCase: DeleteDrinkDetailUseCase
) : ViewModel() {

    private var drinkId: Int? = null

    private val _isSaved = MutableStateFlow(false)
    val isSaved = _isSaved.asStateFlow()


    fun setId(id: Int) {
        drinkId = id
        viewModelScope.launch {
            isSavedUseCase.execute(id)
                .catch { Log.i("SirMarty", it.message ?: "Unknown error") }
                .collect {
                    _isSaved.value = it
                }
        }
    }

    fun onButtonClick() {
        viewModelScope.launch {
            drinkId?.also {
                if (_isSaved.value) {
                    deleteDrinkDetailUseCase.execute(it)
                } else {
                    saveDrinkDetailUseCase.execute(it)
                }
            }
        }
    }
}