package com.ixafrica.netscan.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = MatrixGreen,
    secondary = DarkGreen,
    tertiary = MatrixGreen,
    background = CyberBlack,
    surface = TerminalGray,
    onPrimary = Color.Black,
    onBackground = MatrixGreen,
    onSurface = Color.White
)

@Composable
fun NetScanTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}