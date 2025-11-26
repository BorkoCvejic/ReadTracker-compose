package com.bcoding.readtracker.book.presentation.book_details

import com.bcoding.readtracker.book.domain.model.Book

sealed interface BookDetailsUiEvents {
    object NavigateBack: BookDetailsUiEvents
    data class ShowAddToListDialog(val book: Book): BookDetailsUiEvents
}
