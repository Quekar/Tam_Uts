package com.example.tam_uts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tam_uts.data.DummyData
import com.example.tam_uts.data.Recipe
import com.example.tam_uts.data.SpoonacularRecipeDetail
import com.example.tam_uts.data.SpoonacularRecipeSummary
import com.example.tam_uts.repository.RecipeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val repository = RecipeRepository()

    private val _recipes = MutableStateFlow<List<SpoonacularRecipeSummary>>(emptyList())
    val recipes: StateFlow<List<SpoonacularRecipeSummary>> = _recipes

    private val _randomRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val randomRecipes: StateFlow<List<Recipe>> = _randomRecipes

    private val _recipeDetail = MutableStateFlow<Recipe?>(null)
    val recipeDetail: StateFlow<Recipe?> = _recipeDetail

    private val _bookmarkedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val bookmarkedRecipes: StateFlow<List<Recipe>> = _bookmarkedRecipes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var searchJob: Job? = null

    fun searchRecipes(query: String? = null, cuisine: String? = null) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.searchRecipes(query = query, cuisine = cuisine)
                _recipes.value = response.results
            } catch (e: Exception) {
                val fallback = DummyData.dummyRecipes
                    .filter {
                        (query == null || it.name.contains(query, ignoreCase = true)) &&
                                (cuisine == null || it.origin.contains(cuisine, ignoreCase = true))
                    }
                    .map { SpoonacularRecipeSummary(it.id, it.name, it.imageUrl) }
                _recipes.value = fallback
                _error.value = describeError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadRandomRecipes(number: Int = 10) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.getRandomRecipes(number)
                _randomRecipes.value = response.recipes.map { it.toRecipe() }
            } catch (e: Exception) {
                _randomRecipes.value = DummyData.dummyRecipes.shuffled().take(number)
                _error.value = describeError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getRecipeDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val detail = repository.getRecipeDetail(id)
                _recipeDetail.value = detail.toRecipe()
            } catch (e: Exception) {
                val fallback = DummyData.dummyRecipes.find { it.id == id }
                if (fallback != null) {
                    _recipeDetail.value = fallback
                    _error.value = describeError(e)
                } else {
                    _error.value = "Gagal memuat detail resep: ${describeError(e)}"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun SpoonacularRecipeDetail.toRecipe(): Recipe {
        return Recipe(
            id = id,
            name = title.ifBlank { "Resep tanpa nama" },
            origin = "Spoonacular API",
            imageUrl = image ?: "",
            description = summary ?: "",
            ingredients = extendedIngredients.orEmpty().mapNotNull { it.original },
            instructions = analyzedInstructions.orEmpty()
                .flatMap { it.steps.orEmpty() }
                .mapNotNull { it.step }
        )
    }

    private fun describeError(e: Exception): String {
        return when (e) {
            is retrofit2.HttpException -> when (e.code()) {
                402 -> "Limit harian API sudah habis. Menampilkan data lokal."
                401 -> "API key tidak valid."
                else -> "Server resep bermasalah (kode ${e.code()}). Menampilkan data lokal."
            }
            is java.io.IOException -> "Tidak ada koneksi internet. Menampilkan data lokal."
            else -> "Gagal memuat data resep. Menampilkan data lokal."
        }
    }

    fun toggleBookmark(recipe: Recipe) {
        val current = _bookmarkedRecipes.value.toMutableList()
        if (current.any { it.id == recipe.id }) {
            current.removeAll { it.id == recipe.id }
        } else {
            current.add(recipe)
        }
        _bookmarkedRecipes.value = current
    }

    fun isBookmarked(recipeId: Int): Boolean {
        return _bookmarkedRecipes.value.any { it.id == recipeId }
    }
}