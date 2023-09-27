package com.sirmarty.drinkcrafter.core.categories.data

import retrofit2.Response
import retrofit2.http.GET

interface GetCategoryListService {
    @GET("list.php?c=list")
    suspend fun getCategoryList(): Response<GetCategoryListResponse>
}