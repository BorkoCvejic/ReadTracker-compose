package com.bcoding.readtracker.book.presentation.library.components.library_item

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bcoding.readtracker.book.domain.model.ReadingListOverview

class LibraryItemPreviewProvider: PreviewParameterProvider<LibraryItemStatePreview> {
    override val values: Sequence<LibraryItemStatePreview>
        get() = items.asSequence()

    override fun getDisplayName(index: Int): String? {
        return items[index].displayName
    }

    private val items = listOf(
        LibraryItemStatePreview(
            readingList = ReadingListOverview(
                readingListId = 1L,
                readingListName = "Thrillers",
                bookCount = 1,
            ),
            displayName = "Regular item"
        ),
        LibraryItemStatePreview(
            readingList = ReadingListOverview(
                readingListId = 2L,
                readingListName = "Later",
                bookCount = 1407,
            ),
            displayName = "Short name - lot of books"
        ),
        LibraryItemStatePreview(
            readingList = ReadingListOverview(
                readingListId = 3L,
                readingListName = "Very long name for a list for testing purposes",
                bookCount = 120,
            ),
            displayName = "Long name"
        ),
    )
}

data class LibraryItemStatePreview(
    val readingList: ReadingListOverview,
    val displayName: String
)
