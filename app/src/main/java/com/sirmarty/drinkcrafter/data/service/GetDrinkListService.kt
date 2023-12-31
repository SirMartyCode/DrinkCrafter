package com.sirmarty.drinkcrafter.data.service

import com.sirmarty.drinkcrafter.data.response.DrinkListResponseJSON
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDrinkListService {
    @GET("filter.php")
    suspend fun getDrinkList(@Query("c") categoryName: String): Response<DrinkListResponseJSON>
}