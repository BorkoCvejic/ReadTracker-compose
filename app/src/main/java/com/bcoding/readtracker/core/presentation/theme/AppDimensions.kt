package com.bcoding.readtracker.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimensions(
    val dimen2: Dp = 2.dp,
    val dimen4: Dp = 4.dp,
    val dimen8: Dp = 8.dp,
    val dimen12: Dp = 12.dp,
    val dimen16: Dp = 16.dp,
    val dimen24: Dp = 24.dp,
    val dimen32: Dp = 32.dp,
    val dimen48: Dp = 48.dp,
    val dimen60: Dp = 60.dp,
    val dimen80: Dp = 80.dp,
    val dimen100: Dp = 100.dp,
    val dimen140: Dp = 140.dp,

    val bottomNavIcon: Dp = 24.dp,
    val bottomNavIconAlpha: Float = 0.6f,

    val dividerThickness: Dp = 1.5.dp,
    val dividerHeight: Dp = 12.dp,

    val bookItemIcon: Dp = 32.dp,

    val loadingWidth: Dp = 5.dp,
    val loadingSize: Dp = 40.dp,

    val imageBlur: Dp = 50.dp,

    val elevatedImageCardHeight: Dp = 250.dp,
    val elevationDefault: Dp = 4.dp,
    val elevationSmall: Dp = 2.dp,

    val expandableIconSize: Dp = 20.dp,

    val dialogHeightMax: Dp = 270.dp
)

val LocalAppDimensions = compositionLocalOf { AppDimensions() }

val MaterialTheme.appDimensions: AppDimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalAppDimensions.current