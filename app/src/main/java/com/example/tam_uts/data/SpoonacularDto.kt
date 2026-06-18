package com.example.tam_uts.data

data class SpoonacularSearchResponse(
    val results: List<SpoonacularRecipeSummary> = emptyList()
)

data class SpoonacularRecipeSummary(
    val id: Int,
    val title: String = "",
    val image: String? = null
)

data class SpoonacularRecipeDetail(
    val id: Int,
    val title: String = "",
    val image: String? = null,
    val summary: String? = null,
    val extendedIngredients: List<SpoonacularIngredient>? = null,
    val analyzedInstructions: List<SpoonacularInstruction>? = null
)

data class SpoonacularIngredient(
    val original: String? = null
)

data class SpoonacularInstruction(
    val steps: List<SpoonacularStep>? = null
)

data class SpoonacularStep(
    val step: String? = null
)
