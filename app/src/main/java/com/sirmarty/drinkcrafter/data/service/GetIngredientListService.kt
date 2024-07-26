package com.sirmarty.drinkcrafter.data.service

import com.sirmarty.drinkcrafter.data.response.IngredientListResponseJSON
import retrofit2.Response
import retrofit2.http.GET

interface GetIngredientListService {
    @GET("list.php?i=list")
    suspend fun getIngredientList(): Response<IngredientListResponseJSON>
}