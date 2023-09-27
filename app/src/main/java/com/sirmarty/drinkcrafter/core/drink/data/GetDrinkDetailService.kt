package com.sirmarty.drinkcrafter.core.drink.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDrinkDetailService {
    @GET("lookup.php")
    suspend fun getDrinkDetail(@Query("i") id: Int): Response<DrinkDetailResponseJSON>
}