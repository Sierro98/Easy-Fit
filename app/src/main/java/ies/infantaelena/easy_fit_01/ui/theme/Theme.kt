package ies.infantaelena.easy_fit_01.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primary_light,
    primaryVariant = primaryVariant_ligt,
    secondary = secondary_light,
    secondaryVariant = secondaryVariant_light,
    background = back_light,
    surface = surface_light,
    error = error_light,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    onError = Color.Black,
    onPrimary = Color.Black,
    onBackground = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = primary_light,
    primaryVariant = primaryVariant_ligt,
    secondary = secondary_light,
    secondaryVariant = secondaryVariant_light,
    background = back_light,
    surface = surface_light,
    error = error_light,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    onError = Color.Black,
    onPrimary = Color.Black,
    onBackground = Color.Black,
)

@Composable
fun Easy_fit_01Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}