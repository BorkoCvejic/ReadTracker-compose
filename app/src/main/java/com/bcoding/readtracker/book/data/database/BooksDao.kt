package com.bcoding.readtracker.book.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.bcoding.readtracker.book.data.mappers.toBookEntity
import com.bcoding.readtracker.book.domain.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {
    @Upsert
    suspend fun upsertBook(book: BookEntity)

    @Upsert
    suspend fun createReadingList(readingList: ReadingListEntity)

    @Query("SELECT * FROM ReadingListEntity")
    fun getAllReadingLists(): Flow<List<ReadingListEntity>>

    @Query("DELETE FROM ReadingListEntity WHERE readingListId = :readingListId")
    suspend fun deleteReadingList(readingListId: Long)

    @Query("UPDATE ReadingListEntity SET readingListName = :newReadingListName WHERE readingListId = :readingListId")
    suspend fun renameReadingList(readingListId: Long, newReadingListName: String)

    @Transaction
    suspend fun deleteReadingListWithCleanup(readingListId: Long) {
        val booksInReadingList = getBooksInReadingList(readingListId = readingListId)

        deleteReadingList(readingListId = readingListId)

        // checks if books from the list are in some other lists before deleting them
        booksInReadingList.forEach { bookId ->
            val readingListsCount = getReadingListCountForBook(bookId = bookId)
            if (readingListsCount == 0) {
                deleteBook(bookId = bookId)
            }
        }
    }

    @Transaction
    suspend fun setBookReadingLists(book: Book, readingListIds: List<Long>) {
        removeBookFromAllReadingLists(bookId = book.bookId)

        if (readingListIds.isEmpty()) {
            deleteBook(bookId = book.bookId)
        } else {
            upsertBook(book = book.toBookEntity())

            readingListIds.forEach { readingListId ->
                addBookToReadingList(
                    ReadingListBookCrossRef(
                        readingListId = readingListId,
                        bookId = book.bookId
                    )
                )
            }
        }
    }

    @Query("SELECT readingListId, COUNT(bookId) as bookCount FROM ReadingListBookCrossRef GROUP BY readingListId")
    fun getBookCountForList(): Flow<List<ReadingListCount>>

    @Query("SELECT COUNT(*) FROM ReadingListBookCrossRef WHERE bookId = :bookId")
    suspend fun getReadingListCountForBook(bookId: String): Int

    @Insert(onConflict = IGNORE)
    suspend fun addBookToReadingList(crossRef: ReadingListBookCrossRef)

    @Query("DELETE FROM ReadingListBookCrossRef WHERE bookId = :bookId")
    suspend fun removeBookFromAllReadingLists(bookId: String)

    @Query("DELETE FROM BookEntity WHERE bookId = :bookId")
    suspend fun deleteBook(bookId: String)

    @Query("SELECT bookId FROM ReadingListBookCrossRef WHERE readingListId = :readingListId")
    suspend fun getBooksInReadingList(readingListId: Long): List<String>

    @Query("""
        SELECT BookEntity.* FROM BookEntity
        INNER JOIN ReadingListBookCrossRef ON BookEntity.bookId = ReadingListBookCrossRef.bookId
        WHERE ReadingListBookCrossRef.readingListId = :readingListId
        ORDER BY ReadingListBookCrossRef.addedAt ASC
        LIMIT :limit OFFSET :offset
    """)
    fun getBooksFromReadingListPaginated(
        readingListId: Long,
        offset: Int,
        limit: Int
    ): Flow<List<BookEntity>>

    @Query("SELECT COUNT(*) FROM ReadingListBookCrossRef WHERE readingListId = :readingListId")
    fun getBookCountInReadingList(readingListId: Long): Flow<Int>

    @Transaction
    @Query("SELECT * FROM ReadingListEntity WHERE readingListId = :readingListId")
    fun getReadingListWithBooks(readingListId: Long): Flow<ReadingListWithBooks?>

    @Transaction
    @Query("SELECT * FROM BookEntity WHERE bookId = :bookId")
    fun getBookWithReadingLists(bookId: String): Flow<BookWithReadingLists?>
}
