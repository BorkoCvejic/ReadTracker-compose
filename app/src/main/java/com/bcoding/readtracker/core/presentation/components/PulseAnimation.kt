package com.bcoding.readtracker.core.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.bcoding.readtracker.core.presentation.components.PulseAnimationDefaults.ALPHA_MAX
import com.bcoding.readtracker.core.presentation.components.PulseAnimationDefaults.DELAY_MILLIS_PULSE_ANIMATION
import com.bcoding.readtracker.core.presentation.components.PulseAnimationDefaults.DURATION_MILLIS_PULSE_ANIMATION
import com.bcoding.readtracker.core.presentation.components.PulseAnimationDefaults.EASING_PULSE_ANIMATION
import com.bcoding.readtracker.core.presentation.components.PulseAnimationDefaults.TRANSITION_INITIAL_VALUE
import com.bcoding.readtracker.core.presentation.components.PulseAnimationDefaults.TRANSITION_TARGET_VALUE
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun PulseAnimation(
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition()
    val progress by transition.animateFloat(
        initialValue = TRANSITION_INITIAL_VALUE,
        targetValue = TRANSITION_TARGET_VALUE,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = DURATION_MILLIS_PULSE_ANIMATION,
                delayMillis = DELAY_MILLIS_PULSE_ANIMATION,
                easing = EASING_PULSE_ANIMATION
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier
            .size(MaterialTheme.appDimensions.loadingSize)
            .graphicsLayer {
                scaleX = progress
                scaleY = progress
                alpha = ALPHA_MAX - progress
            }
            .border(
                width = MaterialTheme.appDimensions.loadingWidth,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    )
}

object PulseAnimationDefaults {
    const val TRANSITION_INITIAL_VALUE = 0F
    const val TRANSITION_TARGET_VALUE = 1F
    const val DURATION_MILLIS_PULSE_ANIMATION = 1000
    const val DELAY_MILLIS_PULSE_ANIMATION = 100
    val EASING_PULSE_ANIMATION = LinearEasing
    const val ALPHA_MAX = 1f
}