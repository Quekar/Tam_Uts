package com.example.tam_uts.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tam_uts.components.BookmarkRecipeItem
import com.example.tam_uts.data.Recipe
import com.example.tam_uts.viewmodel.RecipeViewModel

@Composable
fun BookmarksScreen(
    onRecipeClick: (Recipe) -> Unit,
    recipeViewModel: RecipeViewModel = viewModel()
) {
    val bookmarkedRecipes by recipeViewModel.bookmarkedRecipes.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Resep Tersimpan", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))
        
        if (bookmarkedRecipes.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Belum ada resep yang disimpan", color = Color.Gray)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(bookmarkedRecipes) { recipe -> 
                    BookmarkRecipeItem(recipe, onClick = { onRecipeClick(recipe) }) 
                }
            }
        }
    }
}