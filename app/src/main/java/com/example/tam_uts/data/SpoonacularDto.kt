package com.example.tam_uts.data

import com.google.gson.annotations.SerializedName

data class SpoonacularSearchResponse(
    val results: List<SpoonacularRecipeSummary>
)

data class SpoonacularRecipeSummary(
    val id: Int,
    val title: String,
    val image: String
)

data class SpoonacularRecipeDetail(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val extendedIngredients: List<SpoonacularIngredient>,
    val analyzedInstructions: List<SpoonacularInstruction>
)

data class SpoonacularIngredient(
    val original: String
)

data class SpoonacularInstruction(
    val steps: List<SpoonacularStep>
)

data class SpoonacularStep(
    val step: String
)
