package com.example.tam_uts.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tam_uts.components.IngredientItem
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.components.ScreenHeader
import com.example.tam_uts.data.Recipe

@Composable
fun RecipeDetailScreen(recipe: Recipe, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            ScreenHeader(title = "Detail Resep", onBack = onBack)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(recipe.name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(recipe.origin, fontSize = 16.sp, color = Orange500, fontWeight = FontWeight.Medium)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text("Tentang Masakan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(recipe.description, color = Color.Gray, lineHeight = 20.sp)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text("Bahan-bahan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                recipe.ingredients.forEach { ingredient ->
                    IngredientItem(ingredient)
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text("Cara Membuat", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                recipe.instructions.forEachIndexed { index, step ->
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
