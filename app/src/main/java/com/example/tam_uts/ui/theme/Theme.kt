package com.example.tam_uts.ui.theme // Sesuaikan dengan package Anda

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Orange200,
    secondary = White,
    tertiary = Orange700
)

private val LightColorScheme = lightColorScheme(
    primary = Orange500,
    secondary = White,
    tertiary = Orange700,
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = Black,
    onTertiary = White,
    onBackground = Black,
    onSurface = Black,
)

@Composable
fun Tam_UtsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S -> {
            // val context = LocalContext.current
            // if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            // Sementara matikan dynamic color untuk memastikan warna kita yang dipakai
            if (darkTheme) DarkColorScheme else LightColorScheme
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}