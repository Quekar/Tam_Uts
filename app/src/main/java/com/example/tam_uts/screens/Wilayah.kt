package com.example.tam_uts.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.tam_uts.data.Recipe
import com.example.tam_uts.viewmodel.RecipeViewModel

@Composable
fun RegionsScreen(
    onRecipeClick: (Recipe) -> Unit,
    recipeViewModel: RecipeViewModel = viewModel()
) {
    val cuisines = listOf(
        "Indonesian", "Italian", "Japanese", "Thai",
        "Korean", "Chinese", "Mexican", "French", "Indian", "American"
    )

    var selectedFilter by remember { mutableStateOf(cuisines[0]) }
    val apiRecipes by recipeViewModel.recipes.collectAsState()
    val isLoading by recipeViewModel.isLoading.collectAsState()

    // Memuat data dari API setiap kali filter berubah
    LaunchedEffect(selectedFilter) {
        recipeViewModel.searchRecipes(cuisine = selectedFilter)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Eksplorasi Wilayah", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        // Warna teks deskripsi diubah agar dinamis
        Text("Temukan masakan khas dari berbagai belahan dunia", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            items(cuisines) { cuisine ->
                val isSelected = selectedFilter == cuisine
                val displayName = if (cuisine == "Indonesian") "Indonesia" else cuisine
                Button(
                    onClick = { selectedFilter = cuisine },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        // Menggunakan surfaceVariant sebagai ganti LightGray
                        containerColor = if (isSelected) Orange500 else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSelected) Color.White else Orange500
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = displayName)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                            // Warna teks kosong diubah agar dinamis
                            Text("Tidak ada resep ditemukan.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                } else {
                    items(apiRecipes) { apiRecipe ->
                        // Konversi data API ke model Recipe untuk ditampilkan
                        val recipe = Recipe(
                            id = apiRecipe.id,
                            name = apiRecipe.title,
                            origin = if (selectedFilter == "Indonesian") "Indonesia" else selectedFilter,
                            imageUrl = apiRecipe.image,
                            description = "Masakan khas $selectedFilter",
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