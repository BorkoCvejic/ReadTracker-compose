package com.bcoding.readtracker.book.presentation.library

import com.bcoding.readtracker.book.domain.model.ReadingListOverview

data class LibraryState(
    val readingLists: List<ReadingListOverview> = emptyList()
) {
    val existingReadingListNames: List<String>
        get() = readingLists.map { readingList -> readingList.readingListName }
}
