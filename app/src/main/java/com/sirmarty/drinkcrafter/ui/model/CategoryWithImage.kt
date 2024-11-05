package com.sirmarty.drinkcrafter.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class CategoryWithImage(val name: String, @DrawableRes val image: Int)