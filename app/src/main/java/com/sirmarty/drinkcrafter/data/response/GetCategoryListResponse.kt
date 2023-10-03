package com.sirmarty.drinkcrafter.data.response

import com.google.gson.annotations.SerializedName
import com.sirmarty.drinkcrafter.domain.entity.Category

class GetCategoryListResponse(@SerializedName("drinks") val categories: List<Category>)