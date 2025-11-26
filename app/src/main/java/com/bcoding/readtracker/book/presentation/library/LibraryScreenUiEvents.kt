package com.bcoding.readtracker.book.presentation.library

import com.bcoding.readtracker.book.domain.model.ReadingListOverview

sealed interface LibraryScreenUiEvents {
    data class NavigateToBookDetails(val readingListOverview: ReadingListOverview): LibraryScreenUiEvents
    object ShowCreateNewReadingListDialog: LibraryScreenUiEvents
}
