package com.example.tam_uts.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.tam_uts.components.IngredientItem
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.components.ScreenHeader
import com.example.tam_uts.data.Recipe
import com.example.tam_uts.viewmodel.RecipeViewModel

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    recipeViewModel: RecipeViewModel = viewModel()
) {
    val detailedRecipe by recipeViewModel.recipeDetail.collectAsState()
    val isLoading by recipeViewModel.isLoading.collectAsState()
    val bookmarkedRecipes by recipeViewModel.bookmarkedRecipes.collectAsState()

    val isBookmarked = bookmarkedRecipes.any { it.id == recipe.id }

    LaunchedEffect(recipe.id) {
        if (recipe.ingredients.isEmpty()) {
            recipeViewModel.getRecipeDetail(recipe.id)
        }
    }

    val displayRecipe = detailedRecipe ?: recipe

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(1f)) {
                ScreenHeader(title = "Detail Resep", onBack = onBack)
            }
            IconButton(onClick = { recipeViewModel.toggleBookmark(displayRecipe) }) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Simpan Resep",
                    tint = Orange500
                )
            }
        }

        if (isLoading && detailedRecipe == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Orange500)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = displayRecipe.imageUrl,
                    contentDescription = displayRecipe.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(displayRecipe.name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Text(displayRecipe.origin, fontSize = 16.sp, color = Orange500, fontWeight = FontWeight.Medium)

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Tentang Masakan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    val cleanDescription = displayRecipe.description.replace(Regex("<[^>]*>"), "")
                    Text(cleanDescription, color = Color.Gray, lineHeight = 20.sp)

                    Spacer(modifier = Modifier.height(24.dp))

                    if (displayRecipe.ingredients.isNotEmpty()) {
                        Text("Bahan-bahan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        displayRecipe.ingredients.forEach { ingredient ->
                            IngredientItem(ingredient)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    if (displayRecipe.instructions.isNotEmpty()) {
                        Text("Cara Membuat", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        displayRecipe.instructions.forEachIndexed { index, step ->
                            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    text = "${index + 1}.",
                                    fontWeight = FontWeight.Bold,
                                    color = Orange500,
                                    modifier = Modifier.width(24.dp)
                                )
                                Text(
                                    text = step,
                                    color = Color.DarkGray,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}
