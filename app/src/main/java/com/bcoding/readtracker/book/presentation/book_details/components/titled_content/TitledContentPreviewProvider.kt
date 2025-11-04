package com.bcoding.readtracker.book.presentation.book_details.components.titled_content

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bcoding.readtracker.book.presentation.book_details.components.book_chip.BookChip

class TitledContentPreviewProvider: PreviewParameterProvider<TitledContentStatePreview> {
    override val values: Sequence<TitledContentStatePreview>
        get() = content.asSequence()

    override fun getDisplayName(index: Int): String? {
        return content[index].displayName
    }

    val content = listOf(
        TitledContentStatePreview(
            title = "Number of pages",
            content = {
                BookChip {
                    Text(
                        text = "300 pages",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            displayName = "Book chip"
        ),
        TitledContentStatePreview(
            title = "Synopsis",
            content = {
                Text(
                    text = "Example book description",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            displayName = "Non expandable text"
        ),
        TitledContentStatePreview(
            title = "Synopsis",
            isExpandable = true,
            content = {
                Text(
                    text = "Example book description",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            displayName = "Expandable text - not initially expanded"
        ),
        TitledContentStatePreview(
            title = "Synopsis",
            isExpandable = true,
            isInitiallyExpanded = true,
            content = {
                Text(
                    text = "Example book description",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            displayName = "Expandable text - initially expanded"
        )
    )
}

data class TitledContentStatePreview(
    val title: String,
    val isExpandable: Boolean = false,
    val isInitiallyExpanded: Boolean = false,
    val content: @Composable () -> Unit,
    val displayName: String
)