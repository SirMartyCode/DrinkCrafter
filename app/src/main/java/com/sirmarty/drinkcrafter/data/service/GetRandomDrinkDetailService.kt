package com.sirmarty.drinkcrafter.data.service

import com.sirmarty.drinkcrafter.data.response.DrinkDetailResponseJSON
import retrofit2.Response
import retrofit2.http.GET

interface GetRandomDrinkDetailService {
    @GET("random.php")
    suspend fun getRandomDrinkDetail(): Response<DrinkDetailResponseJSON>
}