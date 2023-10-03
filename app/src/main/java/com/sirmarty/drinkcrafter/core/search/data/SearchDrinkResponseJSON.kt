package com.sirmarty.drinkcrafter.core.search.data

import com.google.gson.annotations.SerializedName

data class SearchDrinkResponseJSON(@SerializedName("drinks") val drinks: List<SearchDrinkJSON>)

data class SearchDrinkJSON(
    @SerializedName("idDrink") val id: Int,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val image: String
)