package com.example.tam_uts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import /* .... */ androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam_uts.components.*
import com.example.tam_uts.data.DummyData
import com.example.tam_uts.data.Page
import com.example.tam_uts.data.User

@Composable
fun ProfileScreen(user: User, onNavigate: (Page) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Image(painter = painterResource(id = android.R.drawable.ic_menu_gallery), contentDescription = null, modifier = Modifier.size(100.dp).clip(CircleShape).background(LightGray))
        Text(user.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(user.bio, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))
        ProfileMenuItem(icon = Icons.Default.Person, title = "Edit Profile", onClick = { onNavigate(Page.EDIT_PROFILE) })
        ProfileMenuItem(icon = Icons.Default.Notifications, title = "Notification", onClick = { onNavigate(Page.NOTIFICATIONS) })
        ProfileMenuItem(icon = Icons.Default.LocationOn, title = "Shipping Address", onClick = { onNavigate(Page.SHIPPING_ADDRESS) })
        ProfileMenuItem(icon = Icons.Default.Settings, title = "Settings", onClick = { onNavigate(Page.SETTINGS) })
    }
}

@Composable
fun EditProfileScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(24.dp)) {
        ScreenHeader("Edit Profile", onBack)
        CustomTextField(label = "Name", value = DummyData.dummyUser.name, onValueChange = {})
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Orange500)) {
            Text("Save Changes")
        }
    }
}

@Composable
fun NotificationScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(24.dp)) {
        ScreenHeader("Notifications", onBack)
        Text("Belum ada notifikasi baru.")
    }
}

@Composable
fun ShippingAddressScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(24.dp)) {
        ScreenHeader("Shipping Address", onBack)
        AddressItem(label = "Rumah", address = "Jl. Prof. Dr. Sumantri Brojonegoro No.1", isDefault = true)
    }
}

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(24.dp)) {
        ScreenHeader("Settings", onBack)
        Text("Pengaturan aplikasi.")
    }
}