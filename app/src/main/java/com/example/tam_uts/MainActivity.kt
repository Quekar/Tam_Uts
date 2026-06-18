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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.data.*
import com.example.tam_uts.screens.*
import com.example.tam_uts.ui.theme.Tam_UtsTheme
import com.example.tam_uts.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by rememberSaveable { mutableStateOf(false) }

            Tam_UtsTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp(
                        isDarkMode = isDarkMode,
                        onThemeChange = { isDarkMode = it },
                        onExitApp = { moveTaskToBack(true) }
                    )
                }
            }
        }
    }
}

@Composable
fun MainApp(
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onExitApp: () -> Unit = {}
) {
    val authViewModel: AuthViewModel = viewModel()
    val currentUser by authViewModel.currentUser.collectAsState()

    var currentPage by rememberSaveable { mutableStateOf(if (authViewModel.isLoggedIn()) Page.HOME else Page.LOGIN) }
    var selectedRecipe by remember { mutableStateOf(DummyData.dummyRecipes[0]) }

    val backStack = remember { mutableStateListOf<Page>() }

    val mainTabs = listOf(
        Page.HOME, Page.REGIONS, Page.SEARCH,
        Page.BOOKMARKS, Page.PROFILE
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
                .background(MaterialTheme.colorScheme.background)
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
                Page.BOOKMARKS -> BookmarksScreen(onRecipeClick = navigateToDetail)
                Page.PROFILE -> ProfileScreen(
                    user = currentUser ?: DummyData.dummyUser,
                    onNavigate = { navigateTo(it) },
                    onLogout = {
                        authViewModel.logout()
                        backStack.clear()
                        currentPage = Page.LOGIN
                    }
                )
                Page.DETAIL -> RecipeDetailScreen(
                    recipe = selectedRecipe,
                    onBack = { currentPage = backStack.removeLastOrNull() ?: Page.HOME }
                )
                Page.EDIT_PROFILE -> EditProfileScreen(
                    user = currentUser ?: DummyData.dummyUser,
                    onSave = { updatedUser ->
                        authViewModel.updateUserProfile(updatedUser)
                    },
                    onBack = { currentPage = backStack.removeLastOrNull() ?: Page.PROFILE }
                )
                Page.NOTIFICATIONS -> NotificationScreen(
                    onBack = { currentPage = backStack.removeLastOrNull() ?: Page.PROFILE }
                )
                Page.SETTINGS -> SettingsScreen(
                    isDarkMode = isDarkMode,
                    onThemeChange = onThemeChange,
                    onBack = { currentPage = backStack.removeLastOrNull() ?: Page.PROFILE }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(currentPage: Page, onPageSelected: (Page) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        val items = listOf(
            Triple(Page.HOME,      Icons.Default.Home,   "Home"),
            Triple(Page.REGIONS,   Icons.Default.Place,  "Wilayah"),
            Triple(Page.SEARCH,    Icons.Default.Search, "Cari"),
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
