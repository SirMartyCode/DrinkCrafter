package com.sirmarty.drinkcrafter.data.service

import com.sirmarty.drinkcrafter.data.response.IngredientDetailResponseJSON
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetIngredientDetailService {
    @GET("search.php")
    suspend fun getIngredientDetail(@Query("i") name: String): Response<IngredientDetailResponseJSON>
}