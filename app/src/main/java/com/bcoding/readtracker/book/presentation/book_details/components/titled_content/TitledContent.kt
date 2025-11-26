package com.bcoding.readtracker.book.presentation.book_details.components.titled_content

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.bcoding.readtracker.R
import com.bcoding.readtracker.book.presentation.book_details.components.titled_content.TitledContentAnimations.DEFAULT_ANIMATION_DURATION
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun TitledContent(
    modifier: Modifier = Modifier,
    title: String,
    isExpandable: Boolean = false,
    isInitiallyExpanded: Boolean = false,
    content: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(isInitiallyExpanded) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = DEFAULT_ANIMATION_DURATION),
        label = "arrow_rotation"
    )

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .then(
                    if (isExpandable) {
                        Modifier.clickable(
                            onClick = { isExpanded = !isExpanded },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                    } else {
                        Modifier
                    }
                )
                .padding(vertical = MaterialTheme.appDimensions.dimen4),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.dimen4),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall
            )
            if (isExpandable) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_down),
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    modifier = Modifier
                        .size(MaterialTheme.appDimensions.expandableIconSize)
                        .rotate(rotationAngle)
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen4))
        if (isExpandable) {
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(DEFAULT_ANIMATION_DURATION))
                        + fadeIn(animationSpec = tween(DEFAULT_ANIMATION_DURATION)),
                exit = shrinkVertically(animationSpec = tween(DEFAULT_ANIMATION_DURATION))
                        + fadeOut(animationSpec = tween(DEFAULT_ANIMATION_DURATION))
            ) {
                content()
            }
        } else {
            content()
        }
    }
}

object TitledContentAnimations{
    const val DEFAULT_ANIMATION_DURATION = 300
}

@Preview(
    name = "Light mode",
    showBackground = true,
    backgroundColor = 0xFFFFFBFE
)
@Preview(
    name = "Dark mode",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun TitledContentPreview(
    @PreviewParameter(TitledContentPreviewProvider::class) titledContentStatePreview: TitledContentStatePreview
) {
    ReadTrackerTheme {
        Surface {
            TitledContent(
                title = titledContentStatePreview.title,
                isExpandable = titledContentStatePreview.isExpandable,
                isInitiallyExpanded = titledContentStatePreview.isInitiallyExpanded,
                content = titledContentStatePreview.content
            )
        }
    }
}
