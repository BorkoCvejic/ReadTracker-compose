package com.bcoding.readtracker.book.presentation.library

import com.bcoding.readtracker.book.domain.model.ReadingListOverview

sealed interface LibraryScreenActions {
    data class OnReadingListClick(val readingList: ReadingListOverview) : LibraryScreenActions
    data class OnDeleteReadingListClick(val readingListId: Long) : LibraryScreenActions
    data class OnRenameReadingListClick(val readingListId: Long, val newReadingListName: String) : LibraryScreenActions
    data class OnCreateNewReadingListDialogClick(val readingListName: String): LibraryScreenActions
    object OnCreateNewReadingListFABClick: LibraryScreenActions
}
