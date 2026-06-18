package com.example.tam_uts.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tam_uts.components.*
import com.example.tam_uts.data.Page
import com.example.tam_uts.data.User
import com.example.tam_uts.viewmodel.RecipeViewModel

@Composable
fun ProfileScreen(
    user: User,
    onNavigate: (Page) -> Unit,
    onLogout: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Keluar dari Akun", fontWeight = FontWeight.Bold) },
            text  = { Text("Apakah kamu yakin ingin keluar?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogout()
                }) {
                    Text("Keluar", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Batal", color = Orange500)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profil Saya", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(24.dp))

        Box(contentAlignment = Alignment.BottomEnd) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Surface(
                shape = CircleShape,
                color = Orange500,
                modifier = Modifier
                    .size(32.dp)
                    .offset(x = (-4).dp, y = (-4).dp)
            ) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(user.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(
            user.bio.ifBlank { "Belum ada bio." },
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                ProfileMenuItem(
                    icon  = Icons.Default.Person,
                    title = "Edit Profil",
                    onClick = { onNavigate(Page.EDIT_PROFILE) }
                )
                ProfileMenuItem(
                    icon  = Icons.Default.Notifications,
                    title = "Notifikasi",
                    onClick = { onNavigate(Page.NOTIFICATIONS) }
                )
                ProfileMenuItem(
                    icon  = Icons.Default.Settings,
                    title = "Pengaturan",
                    onClick = { onNavigate(Page.SETTINGS) }
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        TextButton(
            onClick = { showLogoutDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Keluar dari Akun", color = Color.Red, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun EditProfileScreen(
    user: User,
    onSave: (User) -> Unit,
    onBack: () -> Unit
) {
    var name  by remember { mutableStateOf(user.name) }
    var bio   by remember { mutableStateOf(user.bio) }
    var email by remember { mutableStateOf(user.email) }
    var phone by remember { mutableStateOf(user.phone) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader("Edit Profil", onBack)
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Informasi Profil",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Orange500
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Nama Lengkap", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor     = Orange500,
                unfocusedBorderColor   = MaterialTheme.colorScheme.surfaceVariant,
                focusedContainerColor  = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Email", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor     = Orange500,
                unfocusedBorderColor   = MaterialTheme.colorScheme.surfaceVariant,
                focusedContainerColor  = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Nomor Telepon", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor     = Orange500,
                unfocusedBorderColor   = MaterialTheme.colorScheme.surfaceVariant,
                focusedContainerColor  = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Bio", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            placeholder = { Text("Ceritakan sedikit tentang dirimu...") },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor     = Orange500,
                unfocusedBorderColor   = MaterialTheme.colorScheme.surfaceVariant,
                focusedContainerColor  = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (name.isBlank()) {
                    Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                } else {
                    onSave(user.copy(name = name, bio = bio, email = email, phone = phone))
                    Toast.makeText(context, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    onBack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange500),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Simpan Perubahan", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun NotificationScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        ScreenHeader("Notifikasi", onBack)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Belum ada notifikasi baru.", color = Color.Gray)
        }
    }
}


@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    recipeViewModel: RecipeViewModel = viewModel()
) {
    val syncStatus by recipeViewModel.syncStatus.collectAsState()
    val isLoading  by recipeViewModel.isLoading.collectAsState()

    var showSyncDialog by remember { mutableStateOf(false) }

    if (showSyncDialog) {
        AlertDialog(
            onDismissRequest = { showSyncDialog = false },
            title = { Text("Sync Data ke Firebase", fontWeight = FontWeight.Bold) },
            text  = {
                Text(
                    "Ini akan mengunggah 41 resep lokal ke Firestore.\n" +
                            "Dokumen yang sudah ada akan ditimpa. Lanjutkan?"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showSyncDialog = false
                    recipeViewModel.syncLocalToFirestore()
                }) {
                    Text("Ya, Sync", color = Orange500, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSyncDialog = false }) {
                    Text("Batal", color = Color.Gray)
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        ScreenHeader("Pengaturan", onBack)
        Spacer(modifier = Modifier.height(16.dp))

        ListItem(
            headlineContent  = { Text("Versi Aplikasi") },
            trailingContent  = { Text("1.0.0") },
            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

        ListItem(
            headlineContent  = { Text("Bahasa") },
            trailingContent  = { Text("Bahasa Indonesia") },
            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

        ListItem(
            headlineContent = { Text("Mode Gelap") },
            trailingContent = {
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { onThemeChange(it) },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Orange500)
                )
            },
            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

        ListItem(
            headlineContent = { Text("Sync Data ke Firebase") },
            supportingContent = {
                syncStatus?.let { status ->
                    val isError = status.startsWith("Gagal")
                    Text(
                        text = status,
                        fontSize = 12.sp,
                        color = if (isError) Color(0xFFE53935) else Color(0xFF43A047)
                    )
                }
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Sync,
                    contentDescription = null,
                    tint = Orange500
                )
            },
            trailingContent = {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color    = Orange500,
                        strokeWidth = 2.dp
                    )
                } else {
                    TextButton(onClick = { showSyncDialog = true }) {
                        Text(
                            "Sync",
                            color      = Orange500,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
    }
}