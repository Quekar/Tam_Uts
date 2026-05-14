package com.example.tam_uts.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.components.RecipeCard
import com.example.tam_uts.data.DummyData
import com.example.tam_uts.data.Recipe

@Composable
fun HomeScreen(onRecipeClick: (Recipe) -> Unit, onNavClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("TasteMap", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Orange500)
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth().height(100.dp).clickable { onNavClick() },
            colors = CardDefaults.cardColors(containerColor = Orange500)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text("Jelajahi 38 Provinsi Indonesia", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Resep Populer", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(DummyData.dummyRecipes.take(4)) { recipe -> RecipeCard(recipe, onClick = { onRecipeClick(recipe) }) }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Resep Terbaru", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(DummyData.dummyRecipes.drop(4).take(4)) { recipe -> RecipeCard(recipe, onClick = { onRecipeClick(recipe) }) }
        }
    }
}