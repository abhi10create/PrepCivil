package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = PurpleAccent,
  onPrimary = DarkPurpleText,
  primaryContainer = DarkPurpleText,
  onPrimaryContainer = PurpleAccent,
  background = DarkBackground,
  onBackground = TextPrimary,
  surface = DarkBackground,
  onSurface = TextPrimary,
  surfaceVariant = DarkContainer,
  onSurfaceVariant = TextSecondary,
  outline = DarkBorder,
  outlineVariant = DarkBorder,
  error = ErrorRed,
  onError = Color.Black
)

private val LightColorScheme = lightColorScheme(
  primary = Color(0xFF6750A4),
  onPrimary = Color.White,
  primaryContainer = Color(0xFFEADDFF),
  onPrimaryContainer = Color(0xFF21005D),
  background = Color(0xFFFEF7FF),
  onBackground = Color(0xFF1D1B20),
  surface = Color(0xFFFEF7FF),
  onSurface = Color(0xFF1D1B20),
  surfaceVariant = Color(0xFFE7E0EC),
  onSurfaceVariant = Color(0xFF49454F),
  outline = Color(0xFF79747E),
  outlineVariant = Color(0xFFCAC4D0),
  error = Color(0xFFB3261E),
  onError = Color.White
)

@Composable
fun EduStudyTheme(
  darkTheme: Boolean = true,
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

