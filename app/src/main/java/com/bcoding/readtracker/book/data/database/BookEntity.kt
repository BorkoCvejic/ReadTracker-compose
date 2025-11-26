package com.bcoding.readtracker.book.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class BookEntity(
    @PrimaryKey val bookId: String,
    val title: String,
    val imageUrl: String,
    val authors: List<String>,
    val description: String,
    val languages: List<String>,
    val firstPublishedYear: String?,
    val ratingAverage: Double?,
    val ratingCount: Int?,
    val numPages: Int?,
    val numEditions: Int
)
