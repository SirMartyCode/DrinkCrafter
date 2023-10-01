package com.sirmarty.drinkcrafter.core.drink.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.drinkcrafter.core.drink.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.core.drink.domain.usecase.GetDrinkDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailViewModel @Inject constructor(private val getDrinkDetailUseCase: GetDrinkDetailUseCase) : ViewModel() {

    private val _drinkDetail = MutableLiveData<DrinkDetail>()
    val drinkDetail: LiveData<DrinkDetail> = _drinkDetail

    fun getDrinkDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = getDrinkDetailUseCase.execute(id)
                _drinkDetail.value = response
            } catch (e: Exception) {
                Log.i("SirMarty:", "ERROR! - Drink Detail (not implemented yet)")
            }
        }
    }
}