package com.sirmarty.drinkcrafter.data.service

import com.sirmarty.drinkcrafter.data.response.DrinkListResponseJSON
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDrinkListByIngredientService {
    @GET("filter.php")
    suspend fun getDrinkListByIngredient(@Query("i") ingredient: String): Response<DrinkListResponseJSON>
}