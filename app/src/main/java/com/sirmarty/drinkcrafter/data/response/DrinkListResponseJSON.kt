package com.sirmarty.drinkcrafter.data.response

import com.google.gson.annotations.SerializedName

class DrinkListResponseJSON(@SerializedName("drinks") val drinks: List<DrinkJSON>)

class DrinkJSON(
    @SerializedName("idDrink") val id: Int,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val image: String
)