package com.bcoding.readtracker.book.presentation.book_details

import com.bcoding.readtracker.book.domain.model.Book

sealed interface BookDetailsScreenActions {
    data class OnSelectedBookChange(val book: Book): BookDetailsScreenActions
    object OnBackClick: BookDetailsScreenActions
    object OnAddToReadingListClick: BookDetailsScreenActions
    data class OnSaveBookToReadingListsClick(val book: Book, val selectedReadingLists: List<Long>): BookDetailsScreenActions
    data class OnCreateNewReadingListClick(val readingListName: String): BookDetailsScreenActions
}
