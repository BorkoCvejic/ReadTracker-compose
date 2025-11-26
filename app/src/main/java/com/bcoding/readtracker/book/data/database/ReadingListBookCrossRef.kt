package com.bcoding.readtracker.book.data.database

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["readingListId", "bookId"],
    foreignKeys = [
        ForeignKey(
            entity = ReadingListEntity::class,
            parentColumns = ["readingListId"],
            childColumns = ["readingListId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["bookId"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ReadingListBookCrossRef(
    val readingListId: Long,
    val bookId: String
)
