package com.example.tam_uts.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam_uts.components.LightGray
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.components.RegionListItem
import com.example.tam_uts.data.DummyData
import com.example.tam_uts.data.Recipe

@Composable
fun RegionsScreen(onRecipeClick: (Recipe) -> Unit) {
    var selectedFilter by remember { mutableStateOf("Indonesia") }
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Explore Kategori Wilayah", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { selectedFilter = "Indonesia" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = if (selectedFilter == "Indonesia") Orange500 else LightGray, contentColor = if (selectedFilter == "Indonesia") Color.White else Orange500)
            ) { Text("Indonesia") }
            Button(
                onClick = { selectedFilter = "Internasional" },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = if (selectedFilter == "Internasional") Orange500 else LightGray, contentColor = if (selectedFilter == "Internasional") Color.White else Orange500)
            ) { Text("Internasional") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            val filteredOrigins = DummyData.dummyRecipes.filter { it.regionCategory == selectedFilter }.map { it.origin }.distinct()
            items(filteredOrigins) { origin ->
                val originRecipe = DummyData.dummyRecipes.firstOrNull { it.origin == origin }
                RegionListItem(origin = origin, onClick = { if (originRecipe != null) onRecipeClick(originRecipe) })
            }
        }
    }
}