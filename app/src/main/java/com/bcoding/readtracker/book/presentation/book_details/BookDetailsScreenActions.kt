package com.bcoding.readtracker.book.presentation.book_details

import com.bcoding.readtracker.book.domain.model.Book

sealed interface BookDetailsScreenActions {
    data class OnSelectedBookChange(val book: Book): BookDetailsScreenActions
    object OnBackClick: BookDetailsScreenActions
}
