package com.bcoding.readtracker.book.presentation.reading_list

import com.bcoding.readtracker.book.domain.model.Book

data class ReadingListState(
    val savedBooks: List<Book> = emptyList()
)
