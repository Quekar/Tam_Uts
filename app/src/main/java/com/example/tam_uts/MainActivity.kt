package com.example.tam_uts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
                    MainApp(onExitApp = { moveTaskToBack(true) })
                }
            }
        }
    }
}

@Composable
fun MainApp(onExitApp: () -> Unit = {}) {
    var currentPage by rememberSaveable { mutableStateOf(Page.LOGIN) }
    var userState by remember { mutableStateOf(DummyData.dummyUser) }
    var selectedRecipe by remember { mutableStateOf(DummyData.dummyRecipes[0]) }

    val backStack = remember { mutableStateListOf<Page>() }

    val mainTabs = listOf(
        Page.HOME, Page.REGIONS, Page.SEARCH,
        Page.ADD, Page.BOOKMARKS, Page.PROFILE
    )

    fun navigateTo(page: Page) {
        backStack.add(currentPage)
        currentPage = page
    }

    val navigateToDetail: (Recipe) -> Unit = { recipe ->
        selectedRecipe = recipe
        navigateTo(Page.DETAIL)
    }

    BackHandler(enabled = true) {
        when {
            backStack.isNotEmpty() -> {
                currentPage = backStack.removeLast()
            }
            currentPage in mainTabs && currentPage != Page.HOME -> {
                currentPage = Page.HOME
            }
            else -> {
                onExitApp()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentPage in mainTabs) {
                BottomNavigationBar(currentPage = currentPage) { page ->
                    backStack.clear()
                    currentPage = page
                }
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
                Page.LOGIN -> LoginScreen(
                    onLoginSuccess = {
                        backStack.clear()
                        currentPage = Page.HOME
                    },
                    onNavigateToRegister = { navigateTo(Page.REGISTER) }
                )
                Page.REGISTER -> RegisterScreen(
                    onRegisterSuccess = {
                        backStack.clear()
                        currentPage = Page.HOME
                    },
                    onNavigateToLogin = { currentPage = backStack.removeLastOrNull() ?: Page.LOGIN }
                )
                Page.HOME -> HomeScreen(
                    onRecipeClick = navigateToDetail,
                    onNavClick = { navigateTo(Page.REGIONS) }
                )
                Page.REGIONS -> RegionsScreen(onRecipeClick = navigateToDetail)
                Page.SEARCH  -> SearchScreen(onRecipeClick = navigateToDetail)
                Page.ADD     -> AddRecipeScreen(onAddSuccess = {
                    backStack.clear()
                    currentPage = Page.HOME
                })
                Page.BOOKMARKS -> BookmarksScreen(onRecipeClick = navigateToDetail)
                Page.PROFILE -> ProfileScreen(
                    user = userState,
                    onNavigate = { navigateTo(it) }
                )
                Page.DETAIL -> RecipeDetailScreen(
                    recipe = selectedRecipe,
                    onBack = { currentPage = backStack.removeLastOrNull() ?: Page.HOME }
                )
                Page.EDIT_PROFILE -> EditProfileScreen(
                    user = userState,
                    onSave = { updatedUser ->
                        userState = updatedUser
                        DummyData.dummyUser = userState
                    },
                    onBack = { currentPage = backStack.removeLastOrNull() ?: Page.PROFILE }
                )
                Page.NOTIFICATIONS -> NotificationScreen(
                    onBack = { currentPage = backStack.removeLastOrNull() ?: Page.PROFILE }
                )
                Page.SETTINGS -> SettingsScreen(
                    onBack = { currentPage = backStack.removeLastOrNull() ?: Page.PROFILE }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(currentPage: Page, onPageSelected: (Page) -> Unit) {
    NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
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