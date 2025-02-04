package com.example.task2.ui.theme

import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = Color.Gray,
    tertiary = Color(0xFFCF6679),
    background = Color.Black,    // Set background to Black
    surface = Color.Black,       // Set surface to Black
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,  // Set text color to White
    onSurface = Color.White      // Set surface text color to White
)

@Composable
fun Task2Theme(
    darkTheme: Boolean = true,  // Force dark theme
    dynamicColor: Boolean = false, // Disable dynamic colors
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme  // Always use DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
