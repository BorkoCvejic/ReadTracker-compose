package com.bcoding.readtracker.book.presentation.search

import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.core.presentation.UiText

data class SearchState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val books: List<Book> = emptyList(),
    val searchQuery: String = "",
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = false,
    val currentPage: Int = 0
) {
    companion object {
        const val PAGE_SIZE = 10
    }
}
