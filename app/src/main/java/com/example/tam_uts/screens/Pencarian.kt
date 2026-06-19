package com.example.tam_uts.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tam_uts.components.BookmarkRecipeItem
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.data.Recipe
import com.example.tam_uts.viewmodel.RecipeViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onRecipeClick: (Recipe) -> Unit,
    recipeViewModel: RecipeViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val apiRecipes by recipeViewModel.recipes.collectAsState()
    val randomRecipes by recipeViewModel.randomRecipes.collectAsState()
    val isLoading by recipeViewModel.isLoading.collectAsState()
    val error by recipeViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        if (randomRecipes.isEmpty()) {
            recipeViewModel.loadRandomRecipes(10)
        }
    }

    LaunchedEffect(searchQuery) {
        if (searchQuery.length > 2) {
            delay(400)
            recipeViewModel.searchRecipes(searchQuery)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Cari Inspirasi Masak",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Temukan ribuan resep lezat dari seluruh dunia",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {
                if (searchQuery.isNotEmpty()) {
                    recipeViewModel.searchRecipes(searchQuery)
                }
            },
            active = false,
            onActiveChange = { },
            placeholder = { Text("Cari Burger, Pasta, Sup...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Orange500) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Hapus", tint = Color.Gray)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {}

        if (error != null && searchQuery.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(error ?: "", color = Color(0xFFE53935), fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading && searchQuery.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Orange500)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                if (searchQuery.isEmpty()) {
                    item {
                        Text(
                            text = "Rekomendasi Untukmu",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(randomRecipes) { recipe ->
                        BookmarkRecipeItem(recipe, onClick = { onRecipeClick(recipe) })
                    }
                } else {
                    if (apiRecipes.isEmpty() && !isLoading) {
                        item {
                            Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Tidak ada resep ditemukan.", color = Color.Gray)
                            }
                        }
                    } else {
                        item {
                            Text(
                                text = "Hasil Pencarian",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        items(apiRecipes) { apiRecipe ->
                            val recipe = Recipe(
                                id = apiRecipe.id,
                                name = apiRecipe.title,
                                origin = "API",
                                imageUrl = apiRecipe.image ?: "",
                                description = "",
                                ingredients = emptyList(),
                                instructions = emptyList()
                            )
                            BookmarkRecipeItem(recipe, onClick = { onRecipeClick(recipe) })
                        }
                    }
                }
            }
        }
    }
}