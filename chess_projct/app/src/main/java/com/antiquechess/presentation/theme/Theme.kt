package com.antiquechess.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LocalColorScheme = lightColorScheme(
    primary = DarkWalnutBrown,
    secondary = MutedGold,
    tertiary = AgedParchmentVariant,
    background = AgedParchmentBeige,
    surface = AgedParchmentBeige,
    onPrimary = AgedParchmentBeige,
    onSecondary = DarkWalnutBrown,
    onTertiary = DarkText,
    onBackground = DarkText,
    onSurface = DarkText,
    error = ErrorRed
)

private val LocalDarkColorScheme = darkColorScheme(
    primary = MutedGold,
    secondary = DarkWalnutBrown,
    tertiary = AgedParchmentVariant,
    background = DarkWalnutVariant,
    surface = DarkWalnutVariant,
    onPrimary = DarkWalnutBrown,
    onSecondary = AgedParchmentBeige,
    onTertiary = AgedParchmentBeige,
    onBackground = AgedParchmentBeige,
    onSurface = AgedParchmentBeige,
    error = ErrorRed
)

@Composable
fun AntiqueChessTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) LocalDarkColorScheme else LocalColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
