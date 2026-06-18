package com.example.tam_uts.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApiService {
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String? = null,
        @Query("cuisine") cuisine: String? = null,
        @Query("type") type: String? = null,
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int = 10
    ): SpoonacularSearchResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): SpoonacularRecipeDetail

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): SpoonacularRandomResponse
}

data class SpoonacularRandomResponse(
    val recipes: List<SpoonacularRecipeDetail>
)