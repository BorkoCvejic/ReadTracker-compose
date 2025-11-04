package com.bcoding.readtracker.book.data.repository

import com.bcoding.readtracker.book.data.mappers.toBook
import com.bcoding.readtracker.book.data.mappers.toDescription
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.domain.model.Description
import com.bcoding.readtracker.book.domain.repository.BookRepository
import com.bcoding.readtracker.core.data.Api
import com.bcoding.readtracker.core.domain.DataError
import com.bcoding.readtracker.core.domain.Outcome
import com.bcoding.readtracker.core.domain.safeApiCall

class BookRepositoryImpl(
    private val api: Api
): BookRepository {
    override suspend fun searchBooks(query: String): Outcome<List<Book>, DataError> {
        return safeApiCall {
            val response = api.searchBooks(query)
            response.results.map { it.toBook() }
        }
    }

    override suspend fun getDescription(bookId: String): Outcome<Description, DataError> {
        return safeApiCall {
            val response = api.getBookDescription(bookId)
            response.toDescription()
        }
    }
}
