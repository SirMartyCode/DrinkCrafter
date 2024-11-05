package com.sirmarty.drinkcrafter.data.service

import com.sirmarty.drinkcrafter.data.response.DrinkDetailResponseJSON
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDrinkDetailService {
    @GET("lookup.php")
    suspend fun getDrinkDetail(@Query("i") id: Int): Response<DrinkDetailResponseJSON>
}