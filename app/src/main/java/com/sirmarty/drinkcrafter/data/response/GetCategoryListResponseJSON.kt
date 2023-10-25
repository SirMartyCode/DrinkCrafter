package com.sirmarty.drinkcrafter.data.response

import com.google.gson.annotations.SerializedName
import com.sirmarty.drinkcrafter.domain.entity.Category

data class GetCategoryListResponseJSON(@SerializedName("drinks") val categories: List<CategoryJSON>)

fun GetCategoryListResponseJSON.toDomain(): List<Category> {
    return this.categories.map { category -> category.toDomain() }
}

data class CategoryJSON(@SerializedName("strCategory") val name: String)

fun CategoryJSON.toDomain(): Category {
    return Category(this.name)
}