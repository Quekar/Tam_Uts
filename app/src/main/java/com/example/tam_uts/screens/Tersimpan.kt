package com.example.tam_uts.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam_uts.components.BookmarkRecipeItem
import com.example.tam_uts.data.DummyData
import com.example.tam_uts.data.Recipe

@Composable
fun BookmarksScreen(onRecipeClick: (Recipe) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Resep Tersimpan", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(DummyData.dummyRecipes.take(5)) { recipe -> BookmarkRecipeItem(recipe, onClick = { onRecipeClick(recipe) }) }
        }
    }
}