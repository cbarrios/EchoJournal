package com.plcoding.echojournal.core.ui.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary30,
    primaryContainer = Primary50,
    onPrimaryContainer = Primary95,
    onPrimary = Primary100,
    inversePrimary = Secondary80,
    secondary = Secondary30,
    secondaryContainer = Secondary50,
    background = NeutralVariant99,
    surface = Primary100,
    surfaceVariant = SurfaceVariant,
    onSurface = NeutralVariant10,
    onSurfaceVariant = NeutralVariant30,
    outline = NeutralVariant50,
    outlineVariant = NeutralVariant80,
    errorContainer = Error95,
    onErrorContainer = Error20,
    onError = Error100
)

@Composable
fun EchoJournalTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}