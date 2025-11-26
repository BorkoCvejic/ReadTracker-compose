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
import com.bcoding.readtracker.core.presentation.theme.AppColors.Bronze
import com.bcoding.readtracker.core.presentation.theme.AppColors.BronzeDarkMuted
import com.bcoding.readtracker.core.presentation.theme.AppColors.BronzeContainerDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.BronzeDeep
import com.bcoding.readtracker.core.presentation.theme.AppColors.BronzeDeepest
import com.bcoding.readtracker.core.presentation.theme.AppColors.BronzeContainerLight
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyCharcoal
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyDividerDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyDividerLight
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyLilac
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyMauve
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyMedium
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreyNeutral
import com.bcoding.readtracker.core.presentation.theme.AppColors.GreySlate
import com.bcoding.readtracker.core.presentation.theme.AppColors.OnBronzeContainerDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.OnWarmTaupeDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.OnWarmTaupeLight
import com.bcoding.readtracker.core.presentation.theme.AppColors.TerracottaError
import com.bcoding.readtracker.core.presentation.theme.AppColors.RedError
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmBackground
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmContainerDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmContainerLight
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmSurface
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmSurfaceDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmSurfaceVariant
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmTaupeDark
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmTaupeLight

private val LightColorScheme = lightColorScheme(
    primary = Bronze,
    onPrimary = BronzeDeep,
    secondary = WarmTaupeLight,
    onSecondary = OnWarmTaupeLight,
    background = WarmBackground,
    onBackground = WarmSurfaceDark,
    surface = WarmSurface,
    onSurface = WarmSurfaceDark,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = GreyNeutral,
    surfaceContainer = WarmContainerLight,
    secondaryContainer = BronzeContainerLight,
    onSecondaryContainer = Bronze,
    outline = GreyMedium,
    outlineVariant = GreyDividerLight,
    error = RedError,
)

private val DarkColorScheme = darkColorScheme(
    primary = BronzeDarkMuted,
    onPrimary = BronzeDeepest,
    secondary = WarmTaupeDark,
    onSecondary = OnWarmTaupeDark,
    background = WarmSurfaceDark,
    onBackground = GreyLilac,
    surface = GreyCharcoal,
    onSurface = GreyLilac,
    surfaceVariant = GreySlate,
    onSurfaceVariant = GreyLilac,
    surfaceContainer = WarmContainerDark,
    secondaryContainer = BronzeContainerDark,
    onSecondaryContainer = OnBronzeContainerDark,
    outline = GreyMauve,
    outlineVariant = GreyDividerDark,
    error = TerracottaError
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
