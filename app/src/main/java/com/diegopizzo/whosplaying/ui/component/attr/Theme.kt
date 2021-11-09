package com.diegopizzo.whosplaying.ui.component.attr

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColors(
    primary = blueDark,
    primaryVariant = purple700,
    onPrimary = white,
    secondary = teal200,
    secondaryVariant = teal700,
    onSecondary = black
)
private val DarkColors = darkColors(
    primary = blueDark,
    primaryVariant = purple700,
    onPrimary = white,
    secondary = teal200,
    secondaryVariant = teal700,
    onSecondary = black
)

val Colors.card: Color
    get() = blueDark3

val Colors.row: Color
    get() = blueDark3

val Colors.textColor: Color
    get() = white

@Composable
fun WhosPlayingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}