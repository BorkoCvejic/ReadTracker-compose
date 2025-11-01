package com.bcoding.readtracker.core.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object AppShapes {
    val roundedCornerMedium = RoundedCornerShape(16.dp)

    val materialShapes = androidx.compose.material3.Shapes(
        medium = roundedCornerMedium,
    )
}
