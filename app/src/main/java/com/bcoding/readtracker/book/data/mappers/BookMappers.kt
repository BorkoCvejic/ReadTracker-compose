package com.bcoding.readtracker.book.data.mappers

import com.bcoding.readtracker.book.data.dto.SearchedBookDto
import com.bcoding.readtracker.book.data.mappers.CoverUrls.COVER_URL
import com.bcoding.readtracker.book.domain.model.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id.substringAfterLast('/'),
        title = title,
        imageUrl = coverKey?.let { coverKeyArg -> COVER_URL.format(coverKeyArg) }
            ?: coverAlternativeKey?.let { coverAltKeyArg -> COVER_URL.format(coverAltKeyArg) }
            ?: "",
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishedYear = firstPublishYear.toString(),
        ratingAverage = ratingAverage,
        ratingCount = ratingCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}

object CoverUrls {
    const val COVER_URL = "https://covers.openlibrary.org/b/olid/%s-L.jpg"
}