package com.bcoding.readtracker.book.presentation.book_details.components.add_to_list_dialog

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bcoding.readtracker.book.domain.model.ReadingListOverview

class AddToReadingListDialogPreviewProvider: PreviewParameterProvider<AddToReadingListDialogStatePreview> {
    override val values: Sequence<AddToReadingListDialogStatePreview>
        get() = states.asSequence()

    override fun getDisplayName(index: Int): String? {
        return states[index].displayName
    }

    private val readingLists = (1..6).map { readingListId ->
        ReadingListOverview(
            readingListId = readingListId.toLong(),
            readingListName = "List $readingListId",
            bookCount = readingListId
        )
    }

    private val states = listOf(
        AddToReadingListDialogStatePreview(
            readingListOverviews = emptyList(),
            selectedListIds = emptyList(),
            displayName = "No reading lists"
        ),
        AddToReadingListDialogStatePreview(
            readingListOverviews = listOf(
                ReadingListOverview(
                    readingListId = 1,
                    readingListName = "Thrillers",
                    bookCount = 3
                )
            ),
            selectedListIds = emptyList(),
            displayName = "One list"
        ),
        AddToReadingListDialogStatePreview(
            readingListOverviews = readingLists,
            selectedListIds = listOf(2, 3),
            displayName = "Six lists"
        )
    )
}

data class AddToReadingListDialogStatePreview(
    val readingListOverviews: List<ReadingListOverview>,
    val selectedListIds: List<Long>,
    val displayName: String
)
