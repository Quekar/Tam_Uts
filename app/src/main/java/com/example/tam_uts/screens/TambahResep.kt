package com.example.tam_uts.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam_uts.components.AddRecipeField
import com.example.tam_uts.components.LightGray
import com.example.tam_uts.components.Orange500

@Composable
fun AddRecipeScreen(onAddSuccess: () -> Unit) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Form Tambah Resep", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        AddRecipeField("Judul Masakan", title, { title = it }, "Tulis nama masakan...")

        Text("Foto Masakan", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(8.dp)).background(LightGray).clickable { Toast.makeText(context, "Membuka Galeri...", Toast.LENGTH_SHORT).show() },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.AddCircle, contentDescription = "Upload", tint = Orange500, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text("Tap untuk unggah foto", color = Color.Gray, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        AddRecipeField("Pilih Kategori", category, { category = it }, "Pilih Provinsi/Negara...", trailingIcon = Icons.Default.ArrowDropDown)
        AddRecipeField("Bahan-bahan", ingredients, { ingredients = it }, "1. 500gr Daging\n2. Bumbu Halus...", modifier = Modifier.height(100.dp))
        AddRecipeField("Langkah-langkah", steps, { steps = it }, "1. Panaskan wajan...\n2. Masukkan bumbu...", modifier = Modifier.height(120.dp))

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if(title.isNotEmpty() && ingredients.isNotEmpty()) {
                    Toast.makeText(context, "Resep Berhasil Diunggah!", Toast.LENGTH_SHORT).show()
                    onAddSuccess()
                } else {
                    Toast.makeText(context, "Isi form dengan lengkap!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange500),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Unggah Resep", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}