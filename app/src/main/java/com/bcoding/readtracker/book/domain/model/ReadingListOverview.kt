package com.bcoding.readtracker.book.domain.model

data class ReadingListOverview(
    val readingListId: Long = 0,
    val readingListName: String,
    val bookCount: Int = 0
)
