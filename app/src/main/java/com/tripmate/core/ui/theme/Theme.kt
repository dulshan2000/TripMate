package com.tripmate.core.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TripMateTheme(content: @Composable () -> Unit) {
  val scheme = lightColorScheme(
    primary = BluePrimary,
    secondary = CoralAccent,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = NeutralOn,
    onSurface = NeutralOn
  )
  MaterialTheme(colorScheme = scheme, typography = Typography(), content = content)
}
