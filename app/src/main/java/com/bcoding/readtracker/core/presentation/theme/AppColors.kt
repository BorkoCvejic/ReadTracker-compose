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
    val Bronze = Color(0xFFCD7F32)
    val BronzeDeep = Color(0xFF4A2600)
    val BronzeDarkMuted = Color(0xFF9C5A1F)
    val BronzeDeepest = Color(0xFF2A1400)

    // Secondary
    val WarmTaupeLight = Color(0xFF8C6F50)
    val WarmTaupeDark = Color(0xFF7D6953)
    val OnWarmTaupeLight = Color(0xFFF3E9DF)
    val OnWarmTaupeDark = Color(0xFF2A251F)

    // Containers
    val WarmContainerLight = Color(0xFFE6E3DD)
    val WarmContainerDark = Color(0xFF232220)
    val BronzeContainerLight = Color(0xFFF5E8D6)
    val BronzeContainerDark = Color(0xFF5A4130)
    val OnBronzeContainerDark = Color(0xFFF2E6D9)

    // Neutrals - Light
    val GreyVeryLight = Color(0xFFE3E3E3)
    val WarmBackground = Color(0xFFF5F3EE)
    val WarmSurface = Color(0xFFFAF9F6)
    val WarmSurfaceVariant = Color(0xFFEBE9E4)

    // Neutrals - Dark
    val GreyVeryDark = Color(0xFF1C1B1F)
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
    val TerracottaError = Color(0xFFCF6679)

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
