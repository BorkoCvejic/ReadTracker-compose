package com.bcoding.readtracker.book.presentation.book_details.components.checkbox.checkbox_list

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bcoding.readtracker.book.domain.model.ReadingListOverview

class CheckboxListPreviewProvider: PreviewParameterProvider<CheckboxListStatePreview> {
    override val values: Sequence<CheckboxListStatePreview>
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
        CheckboxListStatePreview(
            readingListOverviews = listOf(
                ReadingListOverview(
                    readingListId = 1L,
                    readingListName = "Classics",
                    bookCount = 10
                )
            ),
            selectedLists = setOf(),
            displayName = "Single unchecked"
        ),
        CheckboxListStatePreview(
            readingListOverviews = listOf(
                ReadingListOverview(
                    readingListId = 1L,
                    readingListName = "Classics",
                    bookCount = 10
                )
            ),
            selectedLists = setOf(1L),
            displayName = "Single checked"
        ),
        CheckboxListStatePreview(
            readingListOverviews = readingLists,
            selectedLists = setOf(),
            displayName = "A lot of unchecked"
        ),
        CheckboxListStatePreview(
            readingListOverviews = readingLists,
            selectedLists = setOf(2L, 4L, 5L),
            displayName = "A lot of checked"
        ),
    )
}

data class CheckboxListStatePreview(
    val readingListOverviews: List<ReadingListOverview>,
    val selectedLists: Set<Long>,
    val displayName: String
)
