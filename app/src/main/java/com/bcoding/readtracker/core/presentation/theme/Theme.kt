package com.bcoding.readtracker.core.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.bcoding.readtracker.core.presentation.theme.AppColors.BlueNavy
import com.bcoding.readtracker.core.presentation.theme.AppColors.BlueSoft
import com.bcoding.readtracker.core.presentation.theme.AppColors.BlueSoftLight
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyBlue
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyBlueLight
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyCharcoal
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyDividerDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyDividerLight
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyLilac
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyMauve
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyMedium
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyNeutral
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreySlate
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyVeryDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyVeryLight
import com.bcoding.readtracker.core.presentation.theme.AppColors.PinkError
import com.bcoding.readtracker.core.presentation.theme.AppColors.RedError
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmAccentContainerLight
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmAccentDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmBackground
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmContainerDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmContainerLight
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmSurface
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmSurfaceDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmSurfaceVariant
import com.bcoding.readtracker.core.presentation.theme.AppColors.White

private val LightColorScheme = lightColorScheme(
    primary = BlueSoft,
    onPrimary = White,
    secondary = GreyBlue,
    onSecondary = White,
    background = WarmBackground,
    onBackground = GreyVeryDark,
    surface = WarmSurface,
    onSurface = GreyVeryDark,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = GreyNeutral,
    surfaceContainer = WarmContainerLight,
    secondaryContainer = WarmAccentContainerLight,
    onSecondaryContainer = BlueSoft,
    outline = GreyMedium,
    outlineVariant = GreyDividerLight,
    error = RedError,
)

private val DarkColorScheme = darkColorScheme(
    primary = BlueSoftLight,
    onPrimary = BlueNavy,
    secondary = GreyBlueLight,
    onSecondary = GreyDark,
    background = WarmSurfaceDark,
    onBackground = GreyVeryLight,
    surface = GreyCharcoal,
    onSurface = GreyVeryLight,
    surfaceVariant = GreySlate,
    onSurfaceVariant = GreyLilac,
    surfaceContainer = WarmContainerDark,
    secondaryContainer = WarmAccentDark,
    onSecondaryContainer = BlueSoftLight,
    outline = GreyMauve,
    outlineVariant = GreyDividerDark,
    error = PinkError
)

@Composable
fun ReadTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val appDimensions = AppDimensions()

    CompositionLocalProvider(
        LocalAppDimensions provides appDimensions
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography.ReadTrackerTypography,
            shapes = AppShapes.materialShapes,
            content = content
        )
    }
}
