package com.bcoding.readtracker.book.presentation.book_details.components.book_chip

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun BookChip(
    modifier: Modifier = Modifier,
    chipContent: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .padding(
                vertical = MaterialTheme.appDimensions.dimen8,
                horizontal = MaterialTheme.appDimensions.dimen12
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            chipContent()
        }
    }
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
private fun BookChipPreview(
    @PreviewParameter(BookChipPreviewProvider::class) bookChipStatePreview: BookChipStatePreview
) {
    ReadTrackerTheme {
        Surface {
            BookChip {
                bookChipStatePreview.stringContent?.let { text ->
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                bookChipStatePreview.drawableContent?.let { drawable ->
                    Icon(
                        painter = painterResource(drawable),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
