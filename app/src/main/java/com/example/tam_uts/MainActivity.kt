package com.example.tam_uts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.data.*
import com.example.tam_uts.screens.*
import com.example.tam_uts.ui.theme.Tam_UtsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tam_UtsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    // Navigasi state
    var currentPage by rememberSaveable { mutableStateOf(Page.LOGIN) }
    var previousPage by rememberSaveable { mutableStateOf(Page.HOME) }
    
    // State User agar perubahan profil langsung terlihat di UI
    var userState by remember { mutableStateOf(DummyData.dummyUser) }
    
    // State Resep Detail
    var selectedRecipe by remember { mutableStateOf(DummyData.dummyRecipes[0]) }

    val navigateToDetail: (Recipe) -> Unit = { recipe ->
        selectedRecipe = recipe
        previousPage = currentPage
        currentPage = Page.DETAIL
    }

    val mainTabs = listOf(
        Page.HOME, Page.REGIONS, Page.SEARCH,
        Page.ADD, Page.BOOKMARKS, Page.PROFILE
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentPage in mainTabs) {
                BottomNavigationBar(currentPage = currentPage) { currentPage = it }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            when (currentPage) {
                // Auth
                Page.LOGIN -> LoginScreen(
                    onLoginSuccess = { currentPage = Page.HOME },
                    onNavigateToRegister = { currentPage = Page.REGISTER }
                )
                Page.REGISTER -> RegisterScreen(
                    onRegisterSuccess = { currentPage = Page.HOME },
                    onNavigateToLogin = { currentPage = Page.LOGIN }
                )
                
                // Tabs
                Page.HOME -> HomeScreen(
                    onRecipeClick = navigateToDetail,
                    onNavClick = { currentPage = Page.REGIONS }
                )
                Page.REGIONS -> RegionsScreen(onRecipeClick = navigateToDetail)
                Page.SEARCH  -> SearchScreen(onRecipeClick = navigateToDetail)
                Page.ADD     -> AddRecipeScreen(onAddSuccess = { currentPage = Page.HOME })
                Page.BOOKMARKS -> BookmarksScreen(onRecipeClick = navigateToDetail)
                Page.PROFILE -> ProfileScreen(
                    user = userState,
                    onNavigate = { currentPage = it }
                )

                // Sub-pages
                Page.DETAIL -> RecipeDetailScreen(
                    recipe = selectedRecipe,
                    onBack = { currentPage = previousPage }
                )
                Page.EDIT_PROFILE -> EditProfileScreen(
                    user = userState,
                    onSave = { updatedUser ->
                        userState = updatedUser
                        DummyData.dummyUser = userState
                    },
                    onBack = { currentPage = Page.PROFILE }
                )
                Page.NOTIFICATIONS -> NotificationScreen(onBack = { currentPage = Page.PROFILE })
                Page.SETTINGS -> SettingsScreen(onBack = { currentPage = Page.PROFILE })
            }
        }
    }
}

@Composable
fun BottomNavigationBar(currentPage: Page, onPageSelected: (Page) -> Unit) {
    NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
        val items = listOf(
            Triple(Page.HOME,      Icons.Default.Home,   "Home"),
            Triple(Page.REGIONS,   Icons.Default.Place,  "Wilayah"),
            Triple(Page.SEARCH,    Icons.Default.Search, "Cari"),
            Triple(Page.ADD,       Icons.Default.Add,    "Tambah"),
            Triple(Page.BOOKMARKS, Icons.Default.Star,   "Simpan"),
            Triple(Page.PROFILE,   Icons.Default.Person, "Profil")
        )
        items.forEach { (page, icon, label) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label, fontSize = 10.sp) },
                selected = currentPage == page,
                onClick = { onPageSelected(page) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Orange500,
                    selectedTextColor = Orange500,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Orange500.copy(alpha = 0.1f)
                )
            )
        }
    }
}
