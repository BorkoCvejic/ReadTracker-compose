package com.bcoding.readtracker.book.presentation.reading_list

import com.bcoding.readtracker.book.domain.model.Book

sealed interface ReadingListScreenUiEvents {
    data class NavigateToBookDetails(val book: Book): ReadingListScreenUiEvents
}
