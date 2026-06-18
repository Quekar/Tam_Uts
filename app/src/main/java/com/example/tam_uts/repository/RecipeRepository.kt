package com.example.tam_uts.repository

import com.example.tam_uts.data.SpoonacularApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeRepository {
    private val apiKey = "d171d6e53182488f818a5d14f75b180c"
    private val baseUrl = "https://api.spoonacular.com/"

    private val api = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SpoonacularApiService::class.java)

    suspend fun searchRecipes(query: String? = null, cuisine: String? = null) = 
        api.searchRecipes(query = query, cuisine = cuisine, apiKey = apiKey)
        
    suspend fun getRecipeDetail(id: Int) = api.getRecipeInformation(id, apiKey)
    
    suspend fun getRandomRecipes(number: Int) = api.getRandomRecipes(number, apiKey)
}
