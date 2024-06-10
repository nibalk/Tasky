package com.nibalk.tasky.core.presentation.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val ColorPalette = lightColorScheme(
    primary = TaskyWhite,
    secondary = TaskyBlack,
    tertiary = TaskyBrownLight,
    background = TaskyWhite,
    surface = TaskyWhite,
    onPrimary = TaskyBlack,
    onSecondary = TaskyWhite,
    onBackground = TaskyLightGray,
    onSurface = TaskyBlack
)

@Composable
fun TaskyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColorPalette,
        typography = Typography,
        content = content
    )
}
