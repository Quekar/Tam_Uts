package com.example.tam_uts.repository

import com.example.tam_uts.data.DummyData
import com.example.tam_uts.data.Recipe
import com.example.tam_uts.data.SpoonacularApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeRepository {
    private val apiKey = "ca47a452119643ec91af38878afe59f1"
    private val baseUrl = "https://api.spoonacular.com/"

    private val api = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SpoonacularApiService::class.java)

    private val db = FirebaseFirestore.getInstance()
    private val recipesCollection = db.collection("recipes")

    suspend fun searchRecipes(query: String? = null, cuisine: String? = null) =
        api.searchRecipes(query = query, cuisine = cuisine, apiKey = apiKey)

    suspend fun getRecipeDetail(id: Int) = api.getRecipeInformation(id, apiKey)

    suspend fun getRandomRecipes(number: Int) = api.getRandomRecipes(number, apiKey)

    suspend fun saveRecipeToFirestore(recipe: Recipe, authorId: String = ""): Result<Unit> {
        return try {
            val docId = recipe.id.toString()
            val data = hashMapOf(
                "authorId"       to authorId,
                "name"           to recipe.name,
                "origin"         to recipe.origin,
                "regionCategory" to recipe.regionCategory,
                "imageUrl"       to recipe.imageUrl,
                "description"    to recipe.description,
                "ingredients"    to recipe.ingredients,
                "instructions"   to recipe.instructions
            )
            recipesCollection.document(docId).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun syncLocalRecipesToFirestore(): Result<Int> {
        return try {
            val batch = db.batch()
            DummyData.dummyRecipes.forEach { recipe ->
                val ref = recipesCollection.document(recipe.id.toString())
                val data = hashMapOf(
                    "authorId"       to "local",
                    "name"           to recipe.name,
                    "origin"         to recipe.origin,
                    "regionCategory" to recipe.regionCategory,
                    "imageUrl"       to recipe.imageUrl,
                    "description"    to recipe.description,
                    "ingredients"    to recipe.ingredients,
                    "instructions"   to recipe.instructions
                )
                batch.set(ref, data)
            }
            batch.commit().await()
            Result.success(DummyData.dummyRecipes.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchAndSaveSpoonacularRecipe(
        id: Int,
        authorId: String = ""
    ): Result<Recipe> {
        return try {
            val detail = api.getRecipeInformation(id, apiKey)
            val recipe = Recipe(
                id            = detail.id,
                name          = detail.title.ifBlank { "Resep tanpa nama" },
                origin        = "Spoonacular",
                regionCategory = "Internasional",
                imageUrl      = detail.image ?: "",
                description   = detail.summary?.replace(Regex("<[^>]*>"), "") ?: "",
                ingredients   = detail.extendedIngredients.orEmpty().mapNotNull { it.original },
                instructions  = detail.analyzedInstructions.orEmpty()
                    .flatMap { it.steps.orEmpty() }
                    .mapNotNull { it.step }
            )
            saveRecipeToFirestore(recipe, authorId)
            Result.success(recipe)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getRecipesFromFirestore(): Result<List<Recipe>> {
        return try {
            val snapshot = recipesCollection.get().await()
            val recipes = snapshot.documents.mapNotNull { doc ->
                try {
                    Recipe(
                        id             = doc.id.toIntOrNull() ?: 0,
                        name           = doc.getString("name") ?: "",
                        origin         = doc.getString("origin") ?: "",
                        regionCategory = doc.getString("regionCategory") ?: "Indonesia",
                        imageUrl       = doc.getString("imageUrl") ?: "",
                        description    = doc.getString("description") ?: "",
                        ingredients    = (doc.get("ingredients") as? List<*>)
                            ?.filterIsInstance<String>() ?: emptyList(),
                        instructions   = (doc.get("instructions") as? List<*>)
                            ?.filterIsInstance<String>() ?: emptyList()
                    )
                } catch (e: Exception) { null }
            }
            Result.success(recipes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRecipesByCategory(category: String): Result<List<Recipe>> {
        return try {
            val snapshot = recipesCollection
                .whereEqualTo("regionCategory", category)
                .get().await()
            val recipes = snapshot.documents.mapNotNull { doc ->
                try {
                    Recipe(
                        id             = doc.id.toIntOrNull() ?: 0,
                        name           = doc.getString("name") ?: "",
                        origin         = doc.getString("origin") ?: "",
                        regionCategory = doc.getString("regionCategory") ?: category,
                        imageUrl       = doc.getString("imageUrl") ?: "",
                        description    = doc.getString("description") ?: "",
                        ingredients    = (doc.get("ingredients") as? List<*>)
                            ?.filterIsInstance<String>() ?: emptyList(),
                        instructions   = (doc.get("instructions") as? List<*>)
                            ?.filterIsInstance<String>() ?: emptyList()
                    )
                } catch (e: Exception) { null }
            }
            Result.success(recipes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}