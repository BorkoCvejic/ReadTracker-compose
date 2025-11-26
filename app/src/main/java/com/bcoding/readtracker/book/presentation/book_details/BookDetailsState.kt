package com.bcoding.readtracker.book.presentation.book_details

import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.domain.model.ReadingListOverview
import com.bcoding.readtracker.core.presentation.UiText

data class BookDetailsState(
    val isLoadingDescription: Boolean = false,
    val error: UiText? = null,
    val book: Book? = null,
    val availableReadingLists: List<ReadingListOverview> = emptyList(),
    val selectedListIds: List<Long> = emptyList()
) {
    val existingReadingListNames: List<String>
        get() = availableReadingLists.map { readingList -> readingList.readingListName }
}

