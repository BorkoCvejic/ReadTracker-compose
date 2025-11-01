package com.bcoding.readtracker.core.presentation.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

object AppColors {
    // Primary
    val BlueSoft = Color(0xFF5C6BC0)
    val BlueSoftLight = Color(0xFF9FA8DA)
    val BlueNavy = Color(0xFF001E3C)

    // Containers
    val WarmContainerLight = Color(0xFFE6E3DD)
    val WarmAccentContainerLight = Color(0xFFDCD7EA)
    val WarmContainerDark = Color(0xFF232220)
    val WarmAccentDark = Color(0xFF3A3847)

    // Secondary Greys/Blues
    val GreyBlue = Color(0xFF607D8B)
    val GreyBlueLight = Color(0xFF90A4AE)

    // Neutrals - Light
    val White = Color.White
    val GreyVeryLight = Color(0xFFE3E3E3)
    val WarmBackground = Color(0xFFF5F3EE)
    val WarmSurface = Color(0xFFFAF9F6)
    val WarmSurfaceVariant = Color(0xFFEBE9E4)

    // Neutrals - Dark
    val GreyVeryDark = Color(0xFF1C1B1F)
    val GreyDark = Color(0xFF1B1B1B)
    val WarmSurfaceDark = Color(0xFF242424)
    val GreyCharcoal = Color(0xFF1E1E1E)
    val GreySlate = Color(0xFF2A2A2E)

    // Accent Greys
    val GreyNeutral = Color(0xFF49454F)
    val GreyMedium = Color(0xFF79747E)
    val GreyLilac = Color(0xFFCAC4D0)
    val GreyMauve = Color(0xFF938F99)
    val GreyDividerDark = GreyVeryLight.copy(alpha = 0.12f)
    val GreyDividerLight = GreyVeryDark.copy(alpha = 0.12f)

    // Error/Alert
    val RedError = Color(0xFFB3261E)
    val PinkError = Color(0xFFF2B8B5)

    // Rating
    val WarmOrange = Color(0xFFFFB300)
}

@Preview("Light mode")
@Preview("Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ColorPreview() {
    ReadTrackerTheme(dynamicColor = false) {
        Column(modifier = Modifier.padding(MaterialTheme.appDimensions.dimen16)) {
            MaterialTheme.colorScheme.run {
                listOf(
                    "primary" to primary,
                    "onPrimary" to onPrimary,
                    "secondary" to secondary,
                    "onSecondary" to onSecondary,
                    "background" to background,
                    "onBackground" to onBackground,
                    "surface" to surface,
                    "onSurface" to onSurface,
                    "surfaceVariant" to surfaceVariant,
                    "onSurfaceVariant" to onSurfaceVariant,
                    "surfaceContainer" to surfaceContainer,
                    "secondaryContainer" to secondaryContainer,
                    "onSecondaryContainer" to onSecondaryContainer,
                    "outline" to outline,
                    "outlineVariant" to outlineVariant,
                    "error" to error,
                ).forEach { (name, color) ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(color)
                            .padding(4.dp)
                    ) {
                        Text(name, color = if (color.luminance() < 0.5f) Color.White else Color.Black)
                    }
                }
            }
        }
    }
}