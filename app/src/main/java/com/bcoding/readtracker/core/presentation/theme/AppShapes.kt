package com.bcoding.readtracker.core.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object AppShapes {
    val roundedCornerLarge = RoundedCornerShape(24.dp)
    val roundedCornerMedium = RoundedCornerShape(16.dp)
    val roundedCornerSmall = RoundedCornerShape(8.dp)

    val materialShapes = androidx.compose.material3.Shapes(
        large = roundedCornerLarge,
        medium = roundedCornerMedium,
        small = roundedCornerSmall
    )
}
