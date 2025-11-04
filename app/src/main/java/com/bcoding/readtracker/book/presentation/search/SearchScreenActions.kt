package com.bcoding.readtracker.book.presentation.search

import com.bcoding.readtracker.book.domain.model.Book

sealed interface SearchScreenActions {
    data class OnSearchQueryChange(val query: String) : SearchScreenActions
    data class OnBookClick(val book: Book) : SearchScreenActions
}
