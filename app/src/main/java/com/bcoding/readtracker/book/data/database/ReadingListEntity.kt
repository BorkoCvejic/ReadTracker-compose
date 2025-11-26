package com.bcoding.readtracker.book.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReadingListEntity(
    @PrimaryKey(autoGenerate = true) val readingListId: Long = 0,
    val readingListName: String
)
