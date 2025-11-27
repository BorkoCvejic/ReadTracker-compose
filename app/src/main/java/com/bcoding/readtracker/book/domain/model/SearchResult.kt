package com.bcoding.readtracker.book.domain.model

data class SearchResult(
    val books: List<Book>,
    val hasMore: Boolean
)
