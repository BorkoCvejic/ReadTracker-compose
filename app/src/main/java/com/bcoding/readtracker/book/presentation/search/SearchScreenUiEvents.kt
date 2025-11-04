package com.bcoding.readtracker.book.presentation.search

import com.bcoding.readtracker.book.domain.model.Book

sealed interface SearchScreenUiEvents {
    data class NavigateToBookDetails(val book: Book) : SearchScreenUiEvents
}
