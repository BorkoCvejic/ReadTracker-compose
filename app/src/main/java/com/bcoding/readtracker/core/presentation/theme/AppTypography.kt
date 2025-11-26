package com.bcoding.readtracker.core.presentation.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.bcoding.readtracker.R

object AppTypography {
    val Oswald = FontFamily(
        Font(R.font.oswald_regular, FontWeight.Normal),
        Font(R.font.oswald_medium, FontWeight.Medium),
        Font(R.font.oswald_semi_bold, FontWeight.SemiBold),
        Font(R.font.oswald_bold, FontWeight.Bold)
    )

    private val DefaultFontStyle = TextStyle(
        fontFamily = Oswald
    )

    val ReadTrackerTypography = Typography(
        // Screen titles, major headings
        displayLarge = DefaultFontStyle.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = (-0.5).sp
        ),
        displayMedium = DefaultFontStyle.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 36.sp
        ),
        displaySmall = DefaultFontStyle.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 32.sp
        ),

        // Section headers, card titles
        headlineLarge = DefaultFontStyle.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 28.sp
        ),
        headlineMedium = DefaultFontStyle.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 24.sp
        ),
        headlineSmall = DefaultFontStyle.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp
        ),

        // List item titles, dialog titles
        titleLarge = DefaultFontStyle.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 28.sp
        ),
        titleMedium = DefaultFontStyle.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.15.sp
        ),
        titleSmall = DefaultFontStyle.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),

        // Main content text
        bodyLarge = DefaultFontStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = DefaultFontStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),
        bodySmall = DefaultFontStyle.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),

        // Buttons, tabs, small text
        labelLarge = DefaultFontStyle.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelMedium = DefaultFontStyle.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = DefaultFontStyle.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
    )
}

@Preview("Typography Light")
@Preview("Typography Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TypographyPreview() {
    ReadTrackerTheme(dynamicColor = false) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(MaterialTheme.appDimensions.dimen16)
        ) {
            val typography = MaterialTheme.typography
            val items = listOf(
                "displayLarge" to typography.displayLarge,
                "displayMedium" to typography.displayMedium,
                "displaySmall" to typography.displaySmall,
                "headlineLarge" to typography.headlineLarge,
                "headlineMedium" to typography.headlineMedium,
                "headlineSmall" to typography.headlineSmall,
                "titleLarge" to typography.titleLarge,
                "titleMedium" to typography.titleMedium,
                "titleSmall" to typography.titleSmall,
                "bodyLarge" to typography.bodyLarge,
                "bodyMedium" to typography.bodyMedium,
                "bodySmall" to typography.bodySmall,
                "labelLarge" to typography.labelLarge,
                "labelMedium" to typography.labelMedium,
                "labelSmall" to typography.labelSmall
            )

            items.forEach { (name, style) ->
                Column(
                    modifier = Modifier.padding(
                        vertical = MaterialTheme.appDimensions.dimen2
                    )
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Sample text with $name",
                        style = style,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}
