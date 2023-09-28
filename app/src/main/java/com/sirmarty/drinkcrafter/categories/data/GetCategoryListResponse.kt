package com.sirmarty.drinkcrafter.categories.data

import com.google.gson.annotations.SerializedName
import com.sirmarty.drinkcrafter.categories.domain.entity.Category

class GetCategoryListResponse(@SerializedName("drinks") val categories: List<Category>)