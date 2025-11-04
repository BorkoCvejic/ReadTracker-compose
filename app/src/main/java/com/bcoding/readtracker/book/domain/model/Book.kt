package com.bcoding.readtracker.book.domain.model

data class Book(
    val id: String,
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
