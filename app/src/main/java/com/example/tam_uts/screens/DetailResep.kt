package com.example.tam_uts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam_uts.components.IngredientItem
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.components.ScreenHeader
import com.example.tam_uts.data.Recipe

@Composable
fun RecipeDetailScreen(recipe: Recipe, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.padding(horizontal = 16.dp)) { ScreenHeader(title = "Detail Resep", onBack = onBack) }
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Image(painter = painterResource(id = android.R.drawable.ic_menu_gallery), contentDescription = null, modifier = Modifier.fillMaxWidth().height(200.dp), contentScale = ContentScale.Crop)
            Column(modifier = Modifier.padding(16.dp)) {
                Text(recipe.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(recipe.origin, fontSize = 14.sp, color = Orange500)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Sejarah Masakan", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text("Masakan khas dari ${recipe.origin} ini memiliki sejarah panjang yang kaya akan rempah-rempah.", color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Bahan-bahan", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                listOf("Bahan Utama", "Santan", "Bumbu Halus").forEach { IngredientItem(it) }
            }
        }
    }
}