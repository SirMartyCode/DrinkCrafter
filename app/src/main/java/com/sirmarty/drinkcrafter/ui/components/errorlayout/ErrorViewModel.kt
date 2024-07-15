package com.sirmarty.drinkcrafter.ui.components.errorlayout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sirmarty.drinkcrafter.ui.screens.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class ErrorViewModel<T>: ViewModel() {

    protected val mutableUiState = MutableLiveData<UiState<T>>()
    val uiState: LiveData<UiState<T>> = mutableUiState

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog = _showErrorDialog.asStateFlow()

    abstract fun getData()

    fun retryRequest() {
        getData()
    }

    fun hideErrorDialog() {
        _showErrorDialog.value = false
    }

    protected fun manageErrors(e: Exception) {
        _showErrorDialog.value = true
        mutableUiState.value = UiState.Error(e)
    }
}