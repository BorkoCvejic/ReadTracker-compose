package com.bcoding.readtracker.book.presentation.library

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bcoding.readtracker.book.domain.model.ReadingListOverview

class LibraryScreenPreviewProvider: PreviewParameterProvider<LibraryScreenStatePreview> {
    override val values: Sequence<LibraryScreenStatePreview>
        get() = states.asSequence()

    override fun getDisplayName(index: Int): String? {
        return states[index].displayName
    }

    private val readingLists = (1..15).map { readingListId ->
        ReadingListOverview(
            readingListId = readingListId.toLong(),
            readingListName = "List $readingListId",
            bookCount = readingListId
        )
    }

    private val states = listOf(
        LibraryScreenStatePreview(
            state = LibraryState(
                readingLists = listOf(
                    ReadingListOverview(
                        readingListId = 1L,
                        readingListName = "Thrillers",
                        bookCount = 1,
                    )
                )
            ),
            displayName = "Single item"
        ),
        LibraryScreenStatePreview(
            state = LibraryState(
                readingLists = readingLists
            ),
            displayName = "A lot of lists"
        )
    )
}

data class LibraryScreenStatePreview(
    val state: LibraryState,
    val displayName: String
)
