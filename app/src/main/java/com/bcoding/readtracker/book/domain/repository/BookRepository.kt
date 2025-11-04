package com.bcoding.readtracker.book.domain.repository

import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.domain.model.Description
import com.bcoding.readtracker.core.domain.DataError
import com.bcoding.readtracker.core.domain.Outcome

interface BookRepository {
    suspend fun searchBooks(query: String): Outcome<List<Book>, DataError>
    suspend fun getDescription(bookId: String): Outcome<Description, DataError>
}