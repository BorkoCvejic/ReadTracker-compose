package com.bcoding.readtracker.book.data.mappers

import com.bcoding.readtracker.book.data.database.BookEntity
import com.bcoding.readtracker.book.data.database.ReadingListEntity
import com.bcoding.readtracker.book.data.dto.SearchedBookDto
import com.bcoding.readtracker.book.data.mappers.CoverUrls.COVER_URL
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.domain.model.ReadingListOverview

fun SearchedBookDto.toBook(): Book {
    return Book(
        bookId = bookId.substringAfterLast('/'),
        title = title,
        imageUrl = coverKey?.let { coverKeyArg -> COVER_URL.format(coverKeyArg) }
            ?: coverAlternativeKey?.let { coverAltKeyArg -> COVER_URL.format(coverAltKeyArg) }
            ?: "",
        authors = authorNames ?: emptyList(),
        description = "",
        languages = languages ?: emptyList(),
        firstPublishedYear = firstPublishYear.toString(),
        ratingAverage = ratingAverage,
        ratingCount = ratingCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}

fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        bookId = bookId,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishedYear = firstPublishedYear,
        ratingAverage = ratingAverage,
        ratingCount = ratingCount,
        numPages = numPages,
        numEditions = numEditions
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        bookId = bookId,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishedYear = firstPublishedYear,
        ratingAverage = ratingAverage,
        ratingCount = ratingCount,
        numPages = numPages,
        numEditions = numEditions
    )
}

fun ReadingListEntity.toReadingListOverview(countMap: Map<Long, Int> = emptyMap()): ReadingListOverview {
    return ReadingListOverview(
        readingListId = readingListId,
        readingListName = readingListName,
        bookCount = countMap[readingListId] ?: 0
    )
}

object CoverUrls {
    const val COVER_URL = "https://covers.openlibrary.org/b/olid/%s-L.jpg"
}
