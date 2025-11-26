package com.bcoding.readtracker.book.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        ReadingListEntity::class,
        BookEntity::class,
        ReadingListBookCrossRef::class
    ],
    version = 1
)
@TypeConverters(StringListTypeConverter::class)
abstract class BooksDatabase: RoomDatabase() {
    abstract val booksDao: BooksDao

    companion object {
        const val DB_NAME = "books.db"
    }
}
