package com.example.tam_uts.screens

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam_uts.components.*
import com.example.tam_uts.data.Page
import com.example.tam_uts.data.User

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
                    .background(LightGray)
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
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                ProfileMenuItem(
                    icon = Icons.Default.Person,
                    title = "Edit Profil",
                    onClick = { onNavigate(Page.EDIT_PROFILE) }
                )
                ProfileMenuItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifikasi",
                    onClick = { onNavigate(Page.NOTIFICATIONS) }
                )
                ProfileMenuItem(
                    icon = Icons.Default.Settings,
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
fun EditProfileScreen(user: User, onSave: (User) -> Unit, onBack: () -> Unit) {
    var name  by remember { mutableStateOf(user.name) }
    var bio   by remember { mutableStateOf(user.bio) }
    var email by remember { mutableStateOf(user.email) }
    var phone by remember { mutableStateOf(user.phone) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader("Edit Profil", onBack)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Informasi Profil", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Orange500)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Nama Lengkap", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = name, onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Orange500,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = LightGray,
                unfocusedContainerColor = LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Email", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email, onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Orange500,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = LightGray,
                unfocusedContainerColor = LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Nomor Telepon", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = phone, onValueChange = { phone = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Orange500,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = LightGray,
                unfocusedContainerColor = LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Bio", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = bio, onValueChange = { bio = it },
            modifier = Modifier.fillMaxWidth().height(120.dp),
            placeholder = { Text("Ceritakan sedikit tentang dirimu...") },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Orange500,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = LightGray,
                unfocusedContainerColor = LightGray
            )
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                onSave(user.copy(name = name, bio = bio, email = email, phone = phone))
                onBack()
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
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
fun SettingsScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        ScreenHeader("Pengaturan", onBack)
        Spacer(modifier = Modifier.height(16.dp))
        ListItem(
            headlineContent = { Text("Versi Aplikasi") },
            trailingContent = { Text("1.0.0") }
        )
        HorizontalDivider(color = LightGray)
        ListItem(
            headlineContent = { Text("Bahasa") },
            trailingContent = { Text("Bahasa Indonesia") }
        )
        HorizontalDivider(color = LightGray)
        ListItem(
            headlineContent = { Text("Mode Gelap") },
            trailingContent = { Switch(checked = false, onCheckedChange = {}) }
        )
    }
}