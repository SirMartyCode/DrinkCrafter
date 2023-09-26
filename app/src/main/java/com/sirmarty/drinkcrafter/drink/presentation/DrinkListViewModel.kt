package com.sirmarty.drinkcrafter.drink.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.drink.domain.entity.Drink
import com.sirmarty.drinkcrafter.drink.domain.usecase.GetDrinkListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel @Inject constructor(private val getDrinkListUseCase: GetDrinkListUseCase): ViewModel() {

    private val _drinks = MutableLiveData<List<Drink>>()
    val drinks: LiveData<List<Drink>> = _drinks

    fun getDrinkList(categoryName: String) {
        viewModelScope.launch {
            try {
                val response = getDrinkListUseCase.execute(categoryName)
                _drinks.value = response
            } catch (e: Exception) {
                Log.i("SirMarty:", "ERROR! - Drink List (not implemented yet")
            }
        }
    }
}