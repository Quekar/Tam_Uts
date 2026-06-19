package com.example.tam_uts.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tam_uts.components.BookmarkRecipeItem
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.components.RegionListItem
import com.example.tam_uts.data.DummyData
import com.example.tam_uts.data.Recipe
import com.example.tam_uts.viewmodel.RecipeViewModel

@Composable
fun RegionsScreen(
    onRecipeClick: (Recipe) -> Unit,
    recipeViewModel: RecipeViewModel = viewModel()
) {
    val internationalCuisines = listOf(
        "Italian", "Japanese", "Thai", "Korean", "Chinese", "Mexican", "French", "Indian", "American"
    )
    val tabs = listOf("Indonesia") + internationalCuisines

    var selectedFilter by remember { mutableStateOf(tabs[0]) }
    var selectedRegion by remember { mutableStateOf<String?>(null) }

    val apiRecipes by recipeViewModel.recipes.collectAsState()
    val isLoading by recipeViewModel.isLoading.collectAsState()
    val error by recipeViewModel.error.collectAsState()
    val bookmarkedRecipes by recipeViewModel.bookmarkedRecipes.collectAsState()

    val indonesianRecipes = remember {
        DummyData.dummyRecipes.filter { it.regionCategory == "Indonesia" }
    }
    val indonesianRegions = remember {
        indonesianRecipes.map { it.origin }.distinct()
    }

    BackHandler(enabled = selectedRegion != null) {
        selectedRegion = null
    }

    LaunchedEffect(selectedFilter) {
        selectedRegion = null
        if (selectedFilter != "Indonesia") {
            recipeViewModel.searchRecipes(cuisine = selectedFilter)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text("Eksplorasi Wilayah", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        Text("Temukan masakan khas dari berbagai belahan dunia", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            items(tabs) { tab ->
                val isSelected = selectedFilter == tab
                Button(
                    onClick = { selectedFilter = tab },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Orange500 else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSelected) Color.White else Orange500
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = tab)
                }
            }
        }

        if (selectedFilter != "Indonesia" && error != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(error ?: "", color = Color(0xFFE53935), fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedFilter == "Indonesia") {
            if (selectedRegion == null) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(indonesianRegions) { region ->
                        RegionListItem(origin = region, onClick = { selectedRegion = region })
                    }
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    IconButton(onClick = { selectedRegion = null }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = Orange500)
                    }
                    Text(selectedRegion ?: "", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                val recipesInRegion = indonesianRecipes.filter { it.origin == selectedRegion }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(recipesInRegion) { recipe ->
                        BookmarkRecipeItem(
                            recipe = recipe,
                            isBookmarked = bookmarkedRecipes.any { it.id == recipe.id },
                            onBookmarkClick = { recipeViewModel.toggleBookmark(recipe) },
                            onClick = { onRecipeClick(recipe) }
                        )
                    }
                }
            }
        } else {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Orange500)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (apiRecipes.isEmpty()) {
                        item {
                            Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Tidak ada resep ditemukan.", color = Color.Gray)
                            }
                        }
                    } else {
                        items(apiRecipes) { apiRecipe ->
                            val recipe = Recipe(
                                id = apiRecipe.id,
                                name = apiRecipe.title,
                                origin = selectedFilter,
                                imageUrl = apiRecipe.image ?: "",
                                description = "Masakan khas $selectedFilter",
                                ingredients = emptyList(),
                                instructions = emptyList()
                            )
                            BookmarkRecipeItem(
                                recipe = recipe,
                                isBookmarked = bookmarkedRecipes.any { it.id == recipe.id },
                                onBookmarkClick = { recipeViewModel.toggleBookmark(recipe) },
                                onClick = { onRecipeClick(recipe) }
                            )
                        }
                    }
                }
            }
        }
    }
}