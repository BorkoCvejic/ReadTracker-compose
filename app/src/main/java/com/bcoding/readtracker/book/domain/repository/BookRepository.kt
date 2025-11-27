package com.bcoding.readtracker.book.domain.repository

import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.domain.model.ReadingListOverview
import com.bcoding.readtracker.book.domain.model.Description
import com.bcoding.readtracker.book.domain.model.SearchResult
import com.bcoding.readtracker.core.domain.DataError
import com.bcoding.readtracker.core.domain.Outcome
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    // API
    suspend fun searchBooks(query: String, offset: Int, limit: Int): Outcome<SearchResult, DataError>
    suspend fun getDescription(bookId: String): Outcome<Description, DataError>

    // Database
    fun getAllReadingLists(): Flow<List<ReadingListOverview>>
    suspend fun createReadingList(name: String)
    suspend fun renameReadingList(readingListId: Long, newReadingListName: String)
    suspend fun deleteReadingList(readingListId: Long)
    suspend fun setBookReadingLists(book: Book, readingListIds: List<Long>)
    fun getBooksFromReadingList(readingListId: Long): Flow<List<Book>>
    fun getReadingListsForBook(bookId: String): Flow<List<Long>>
}
