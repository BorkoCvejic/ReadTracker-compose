package com.bcoding.readtracker.book.presentation.search

sealed interface SearchScreenUiEvents {
    data class NavigateToBookDetails(val bookId: String) : SearchScreenUiEvents
}
