package com.example.tam_uts.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tam_uts.components.LightGray
import com.example.tam_uts.components.Orange500
import com.example.tam_uts.viewmodel.AuthUiState
import com.example.tam_uts.viewmodel.AuthViewModel


private enum class PasswordStrength(val label: String, val color: Color, val filledBars: Int) {
    EMPTY("", Color.Transparent, 0),
    WEAK("Lemah", Color(0xFFE53935), 1),
    FAIR("Sedang", Color(0xFFFF9800), 2),
    GOOD("Bagus", Color(0xFFFFC107), 3),
    STRONG("Kuat", Color(0xFF43A047), 4)
}

private fun evaluatePassword(password: String): PasswordStrength {
    if (password.isEmpty()) return PasswordStrength.EMPTY
    var score = 0
    if (password.length >= 8) score++
    if (password.any { it.isUpperCase() }) score++
    if (password.any { it.isDigit() }) score++
    if (password.any { !it.isLetterOrDigit() }) score++
    return when (score) {
        0, 1 -> PasswordStrength.WEAK
        2    -> PasswordStrength.FAIR
        3    -> PasswordStrength.GOOD
        else -> PasswordStrength.STRONG
    }
}

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by authViewModel.uiState.collectAsState()

    var firstName   by remember { mutableStateOf("") }
    var lastName    by remember { mutableStateOf("") }
    var email       by remember { mutableStateOf("") }
    var phone       by remember { mutableStateOf("") }
    var password    by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var passVisible by remember { mutableStateOf(false) }
    var confVisible by remember { mutableStateOf(false) }
    var agreedTerms by remember { mutableStateOf(false) }

    val strength = evaluatePassword(password)

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AuthUiState.Success -> {
                Toast.makeText(context, "Akun berhasil dibuat!", Toast.LENGTH_SHORT).show()
                authViewModel.resetState()
                onRegisterSuccess()
            }
            is AuthUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                authViewModel.resetState()
            }
            else -> Unit
        }
    }

    val isLoading = uiState is AuthUiState.Loading

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Orange500)
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp, bottom = 28.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable(enabled = !isLoading) { onNavigateToLogin() }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Kembali",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Bergabung sekarang",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White.copy(alpha = 0.85f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Buat akun & mulai\njelajahi resep nusantara",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    lineHeight = 28.sp
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)) {

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    RegisterFieldLabel("Nama Depan")
                    RegisterTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = "Andi",
                        enabled = !isLoading,
                        leadingIcon = {
                            Icon(Icons.Default.Person, null, tint = Orange500, modifier = Modifier.size(18.dp))
                        }
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    RegisterFieldLabel("Nama Belakang")
                    RegisterTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholder = "Pratama",
                        enabled = !isLoading,
                        leadingIcon = {
                            Icon(Icons.Default.Person, null, tint = Orange500, modifier = Modifier.size(18.dp))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            RegisterFieldLabel("Email")
            RegisterTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "contoh@email.com",
                keyboardType = KeyboardType.Email,
                enabled = !isLoading,
                leadingIcon = {
                    Icon(Icons.Default.Email, null, tint = Orange500, modifier = Modifier.size(18.dp))
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            RegisterFieldLabel("Nomor Telepon")
            RegisterTextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder = "+62 812 xxxx xxxx",
                keyboardType = KeyboardType.Phone,
                enabled = !isLoading,
                leadingIcon = {
                    Icon(Icons.Default.Phone, null, tint = Orange500, modifier = Modifier.size(18.dp))
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            RegisterFieldLabel("Password")
            RegisterTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Min. 8 karakter",
                keyboardType = KeyboardType.Password,
                enabled = !isLoading,
                visualTransformation = if (passVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(Icons.Default.Lock, null, tint = Orange500, modifier = Modifier.size(18.dp))
                },
                trailingIcon = {
                    TextButton(onClick = { passVisible = !passVisible }) {
                        Text(
                            if (passVisible) "Sembunyikan" else "Lihat",
                            fontSize = 11.sp,
                            color = Orange500,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
            if (password.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                PasswordStrengthBar(strength = strength)
                Text(
                    "Kekuatan: ${strength.label}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = strength.color,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            RegisterFieldLabel("Konfirmasi Password")
            RegisterTextField(
                value = confirmPass,
                onValueChange = { confirmPass = it },
                placeholder = "Ulangi password",
                keyboardType = KeyboardType.Password,
                enabled = !isLoading,
                visualTransformation = if (confVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(Icons.Default.Lock, null, tint = Orange500, modifier = Modifier.size(18.dp))
                },
                trailingIcon = {
                    TextButton(onClick = { confVisible = !confVisible }) {
                        Text(
                            if (confVisible) "Sembunyikan" else "Lihat",
                            fontSize = 11.sp,
                            color = Orange500,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                isError = confirmPass.isNotEmpty() && confirmPass != password
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = agreedTerms,
                    onCheckedChange = { agreedTerms = it },
                    enabled = !isLoading,
                    colors = CheckboxDefaults.colors(checkedColor = Orange500)
                )
                Text(
                    "Saya setuju dengan Syarat & Ketentuan",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (!agreedTerms) {
                        Toast.makeText(context, "Harap setujui syarat & ketentuan!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    authViewModel.register(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        phone = phone,
                        password = password,
                        confirmPassword = confirmPass
                    )
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Orange500),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                } else {
                    Text("Daftar Sekarang", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Sudah punya akun? ", fontSize = 14.sp, color = Color.Gray)
                TextButton(onClick = onNavigateToLogin) {
                    Text("Masuk di sini", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Orange500)
                }
            }
        }
    }
}

@Composable
private fun RegisterFieldLabel(label: String) {
    Text(
        label,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color.DarkGray,
        modifier = Modifier.padding(bottom = 5.dp)
    )
}

@Composable
private fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.LightGray, fontSize = 13.sp) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        isError = isError,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Orange500,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = LightGray,
            unfocusedContainerColor = LightGray
        )
    )
}

@Composable
private fun PasswordStrengthBar(strength: PasswordStrength) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
        repeat(4) { index ->
            val filled = index < strength.filledBars
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(if (filled) strength.color else Color(0xFFE5E5E5))
            )
        }
    }
}