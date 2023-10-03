package com.sirmarty.drinkcrafter.data.service

import com.sirmarty.drinkcrafter.data.response.GetCategoryListResponse
import retrofit2.Response
import retrofit2.http.GET

interface GetCategoryListService {
    @GET("list.php?c=list")
    suspend fun getCategoryList(): Response<GetCategoryListResponse>
}