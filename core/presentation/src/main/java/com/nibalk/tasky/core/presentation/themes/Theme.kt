package com.nibalk.tasky.core.presentation.themes

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


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
    // Set status bar color to be transparent and it's icons to be lighter
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = ColorPalette,
        typography = Typography,
        content = content
    )
}
