package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light Schemes
private val CustomLightPurple = lightColorScheme(primary = Purple40, secondary = PurpleGrey40, tertiary = Pink40)
private val CustomLightEmerald = lightColorScheme(primary = Emerald40, secondary = EmeraldGrey40, tertiary = Teal40, background = Color(0xFFF0FDF4), surface = Color(0xFFF0FDF4))
private val CustomLightOcean = lightColorScheme(primary = Ocean40, secondary = OceanGrey40, tertiary = Sky40, background = Color(0xFFEFF6FF), surface = Color(0xFFEFF6FF))
private val CustomLightSunset = lightColorScheme(primary = Sunset40, secondary = SunsetGrey40, tertiary = Coral40, background = Color(0xFFFEF2F2), surface = Color(0xFFFEF2F2))
private val CustomLightSlate = lightColorScheme(primary = Slate40, secondary = SlateGrey40, tertiary = Silver40, background = Color(0xFFF8FAFC), surface = Color(0xFFF8FAFC))

// Dark Schemes
private val CustomDarkPurple = darkColorScheme(primary = Purple80, secondary = PurpleGrey80, tertiary = Pink80)
private val CustomDarkEmerald = darkColorScheme(primary = Emerald80, secondary = EmeraldGrey80, tertiary = Teal80, background = Color(0xFF022C22), surface = Color(0xFF022C22))
private val CustomDarkOcean = darkColorScheme(primary = Ocean80, secondary = OceanGrey80, tertiary = Sky80, background = Color(0xFF1E3A8A), surface = Color(0xFF1E3A8A))
private val CustomDarkSunset = darkColorScheme(primary = Sunset80, secondary = SunsetGrey80, tertiary = Coral80, background = Color(0xFF451A03), surface = Color(0xFF451A03))
private val CustomDarkSlate = darkColorScheme(primary = Slate80, secondary = SlateGrey80, tertiary = Silver80, background = Color(0xFF0F172A), surface = Color(0xFF0F172A))

@Composable
fun MyApplicationTheme(
    themeMode: String = "SYSTEM",
    colorStyle: String = "PURPLE", // "EMERALD", "OCEAN", "SUNSET", "SLATE", "PURPLE"
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        "LIGHT" -> false
        "DARK" -> true
        else -> isSystemInDarkTheme()
    }

    val colorScheme = if (darkTheme) {
        when (colorStyle) {
            "EMERALD" -> CustomDarkEmerald
            "OCEAN" -> CustomDarkOcean
            "SUNSET" -> CustomDarkSunset
            "SLATE" -> CustomDarkSlate
            else -> CustomDarkPurple
        }
    } else {
        when (colorStyle) {
            "EMERALD" -> CustomLightEmerald
            "OCEAN" -> CustomLightOcean
            "SUNSET" -> CustomLightSunset
            "SLATE" -> CustomLightSlate
            else -> CustomLightPurple
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
