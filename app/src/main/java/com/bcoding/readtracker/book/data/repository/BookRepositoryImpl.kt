package com.bcoding.readtracker.book.data.repository

import androidx.room.Transaction
import com.bcoding.readtracker.book.data.database.ReadingListEntity
import com.bcoding.readtracker.book.data.database.BooksDao
import com.bcoding.readtracker.book.data.mappers.toBook
import com.bcoding.readtracker.book.data.mappers.toReadingListOverview
import com.bcoding.readtracker.book.data.mappers.toDescription
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.domain.model.ReadingListOverview
import com.bcoding.readtracker.book.domain.model.Description
import com.bcoding.readtracker.book.domain.model.SearchResult
import com.bcoding.readtracker.book.domain.repository.BookRepository
import com.bcoding.readtracker.core.data.Api
import com.bcoding.readtracker.core.domain.DataError
import com.bcoding.readtracker.core.domain.Outcome
import com.bcoding.readtracker.core.domain.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val api: Api,
    private val booksDao: BooksDao
): BookRepository {
    override suspend fun searchBooks(query: String, offset: Int, limit: Int): Outcome<SearchResult, DataError> {
        return safeApiCall {
            val response = api.searchBooks(
                query = query,
                offset = offset,
                limit = limit
            )

            SearchResult(
                books = response.results.map { searchedBookDto ->
                    searchedBookDto.toBook()
                },
                hasMore = response.resultsSize > (offset + limit)
            )
        }
    }

    override suspend fun getDescription(bookId: String): Outcome<Description, DataError> {
        return safeApiCall {
            val response = api.getBookDescription(bookId)
            response.toDescription()
        }
    }

    override fun getAllReadingLists(): Flow<List<ReadingListOverview>> =
        combine(
            booksDao.getAllReadingLists(),
            booksDao.getBookCountForList()
        ) { entities, counts ->
            val countMap = counts.associateBy(
                { listBookCount -> listBookCount.readingListId },
                { listBookCount -> listBookCount.bookCount })
            entities.map { entity ->
                entity.toReadingListOverview(countMap)
            }
        }

    override suspend fun createReadingList(name: String) =
        booksDao.createReadingList(readingList = ReadingListEntity(readingListName = name))

    override suspend fun renameReadingList(readingListId: Long, newReadingListName: String) =
        booksDao.renameReadingList(readingListId = readingListId, newReadingListName = newReadingListName)

    override suspend fun deleteReadingList(readingListId: Long) =
        booksDao.deleteReadingListWithCleanup(readingListId = readingListId)

    @Transaction
    override suspend fun setBookReadingLists(book: Book, readingListIds: List<Long>) =
        booksDao.setBookReadingLists(book = book, readingListIds = readingListIds)

    override fun getBooksFromReadingListPaginated(
        readingListId: Long,
        offset: Int,
        limit: Int,
    ): Flow<List<Book>> =
        booksDao
            .getBooksFromReadingListPaginated(
                readingListId = readingListId,
                offset = offset,
                limit = limit
            )
            .map { bookEntities ->
                bookEntities.map { bookEntity ->
                    bookEntity.toBook()
                }
            }

    override fun getBookCountInReadingList(readingListId: Long): Flow<Int> =
        booksDao.getBookCountInReadingList(readingListId)

    override fun getReadingListsForBook(bookId: String): Flow<List<Long>> =
        booksDao
            .getBookWithReadingLists(bookId)
            .map { bookWithReadingLists ->
                bookWithReadingLists?.readingLists?.map { readingListEntity ->
                    readingListEntity.readingListId
                } ?: emptyList()
            }
}
