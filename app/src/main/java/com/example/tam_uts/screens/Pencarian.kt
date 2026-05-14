package com.example.tam_uts.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tam_uts.components.BookmarkRecipeItem
import com.example.tam_uts.components.LightGray
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.data.DummyData
import com.example.tam_uts.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(onRecipeClick: (Recipe) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { },
            active = false,
            onActiveChange = { },
            placeholder = { Text("Cari Nasi Goreng, Rendang...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Orange500) },
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(LightGray)
        ) {}
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val searchResults = if(searchQuery.isEmpty()) DummyData.dummyRecipes else DummyData.dummyRecipes.filter { it.name.contains(searchQuery, ignoreCase = true) || it.origin.contains(searchQuery, ignoreCase = true) }
            items(searchResults) { recipe -> BookmarkRecipeItem(recipe, onClick = { onRecipeClick(recipe) }) }
        }
    }
}