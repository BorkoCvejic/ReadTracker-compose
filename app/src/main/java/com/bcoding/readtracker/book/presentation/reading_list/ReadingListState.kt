package com.bcoding.readtracker.book.presentation.reading_list

import com.bcoding.readtracker.book.domain.model.Book

data class ReadingListState(
    val savedBooks: List<Book> = emptyList(),
    val hasMore: Boolean = false,
    val currentPage: Int = 0,
    val isLoadingMore: Boolean = false,
    val totalBooksCount: Int = 0
) {
    companion object {
        const val PAGE_SIZE = 10
    }
}
