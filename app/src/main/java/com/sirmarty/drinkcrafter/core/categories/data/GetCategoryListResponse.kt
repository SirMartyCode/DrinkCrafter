package com.sirmarty.drinkcrafter.core.categories.data

import com.google.gson.annotations.SerializedName
import com.sirmarty.drinkcrafter.core.categories.domain.entity.Category

class GetCategoryListResponse(@SerializedName("drinks") val categories: List<Category>)