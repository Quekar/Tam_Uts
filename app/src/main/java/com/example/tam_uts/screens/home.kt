package com.example.tam_uts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tam_uts.R
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.components.RecipeCard
import com.example.tam_uts.data.Recipe
import com.example.tam_uts.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(
    onRecipeClick: (Recipe) -> Unit,
    onNavClick: () -> Unit,
    recipeViewModel: RecipeViewModel = viewModel()
) {
    val recipes by recipeViewModel.randomRecipes.collectAsState()
    val isLoading by recipeViewModel.isLoading.collectAsState()
    val bookmarkedRecipes by recipeViewModel.bookmarkedRecipes.collectAsState()

    LaunchedEffect(Unit) {
        if (recipes.isEmpty()) {
            recipeViewModel.loadRandomRecipes(10)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.logo_tastemap),
                contentDescription = "Logo TasteMap",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("TasteMap", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Orange500)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth().height(100.dp).clickable { onNavClick() },
            colors = CardDefaults.cardColors(containerColor = Orange500)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text("Jelajahi Wilayah Indonesia", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading && recipes.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Orange500)
            }
        } else {
            Text("Inspirasi Masak", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(recipes) { recipe ->
                    RecipeCard(
                        recipe = recipe,
                        isBookmarked = bookmarkedRecipes.any { it.id == recipe.id },
                        onBookmarkClick = { recipeViewModel.toggleBookmark(recipe) },
                        onClick = { onRecipeClick(recipe) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}