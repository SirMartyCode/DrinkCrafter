package com.sirmarty.drinkcrafter.data.service

import com.sirmarty.drinkcrafter.data.response.SearchDrinkResponseJSON
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchDrinkByNameService {
    @GET("search.php")
    suspend fun searchDrinkByName(@Query("s") text: String): Response<SearchDrinkResponseJSON>
}