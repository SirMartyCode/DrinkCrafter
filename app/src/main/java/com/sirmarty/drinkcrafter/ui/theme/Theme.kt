package com.sirmarty.drinkcrafter.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColorScheme(
    primary = Verdigris,
    onPrimary = DarkTeal,
    primaryContainer = LightTeal,
    secondary = Teal,
    tertiary = FakeWhite,
    surface = PastelTeal,
    surfaceContainer = White,
    onSurface = RaisinBlack,
    background = PastelTeal,
    onBackground = RaisinBlack
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkGrey,
    onPrimary = Verdigris,
    primaryContainer = CharcoalGray,
    secondary = Teal,
    tertiary = CharcoalGray,
    surface = RaisinBlack,
    surfaceContainer = CharcoalGray,
    onSurface = White,
    background = RaisinBlack,
    onBackground = White
)

@Composable
fun DrinkCrafterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = colorScheme.primaryContainer.toArgb()

            // We set the appearance of the status bar to have the opposite colors of the
            // system theme so that its contents can be seen
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = shapes
    )
}