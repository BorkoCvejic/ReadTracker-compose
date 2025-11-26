package com.bcoding.readtracker.book.data.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ReadingListWithBooks(
    @Embedded val readingList: ReadingListEntity,
    @Relation(
        parentColumn = "readingListId",
        entityColumn = "bookId",
        associateBy = Junction(ReadingListBookCrossRef::class)
    )
    val books: List<BookEntity>
)

data class BookWithReadingLists(
    @Embedded val book: BookEntity,
    @Relation(
        parentColumn = "bookId",
        entityColumn = "readingListId",
        associateBy = Junction(ReadingListBookCrossRef::class)
    )
    val readingLists: List<ReadingListEntity>
)
