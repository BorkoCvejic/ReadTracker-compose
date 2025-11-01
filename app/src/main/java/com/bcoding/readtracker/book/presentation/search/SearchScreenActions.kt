package com.bcoding.readtracker.book.presentation.search

sealed interface SearchScreenActions {
    data class OnSearchQueryChange(val query: String) : SearchScreenActions
    data class OnBookClick(val bookId: String) : SearchScreenActions
}
