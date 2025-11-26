package com.bcoding.readtracker.book.presentation.reading_list.components.reading_list_item

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bcoding.readtracker.book.domain.model.Book

class ReadingListItemProvider: PreviewParameterProvider<ReadingListItemStatePreview> {
    override val values: Sequence<ReadingListItemStatePreview>
        get() = books.asSequence()

    override fun getDisplayName(index: Int): String? {
        return books[index].displayName
    }

    val books = listOf(
        ReadingListItemStatePreview(
            book = Book(
                bookId = 123.toString(),
                title = "Harry Potter",
                imageUrl = "https://test.com",
                authors = listOf("JK Rowling"),
                description = "Description about Harry Potter book",
                languages = emptyList(),
                firstPublishedYear = "2005",
                ratingAverage = 4.6757,
                ratingCount = 200000,
                numPages = 300,
                numEditions = 3
            ),
            displayName = "Regular item"
        ),
        ReadingListItemStatePreview(
            book = Book(
                bookId = 123.toString(),
                title = "Harry Potter",
                imageUrl = "https://test.com",
                authors = emptyList(),
                description = "Description about Harry Potter book",
                languages = emptyList(),
                firstPublishedYear = "2005",
                ratingAverage = 4.6757,
                ratingCount = 200000,
                numPages = 300,
                numEditions = 3
            ),
            displayName = "No author"
        ),
        ReadingListItemStatePreview(
            book = Book(
                bookId = 123.toString(),
                title = "A Very Long Book Title That Should Probably Be Shortened In The UI To Avoid Overflowing The Layout Bounds",
                imageUrl = "https://test.com",
                authors = listOf("JK Rowling"),
                description = "Description about Very Long Title book",
                languages = emptyList(),
                firstPublishedYear = "2005",
                ratingAverage = 4.6757,
                ratingCount = 200000,
                numPages = 300,
                numEditions = 3
            ),
            displayName = "Long title"
        ),
        ReadingListItemStatePreview(
            book = Book(
                bookId = 123.toString(),
                title = "Harry Potter",
                imageUrl = "https://test.com",
                authors = listOf("JK Rowling"),
                description = "Description about Harry Potter book",
                languages = emptyList(),
                firstPublishedYear = "2005",
                ratingAverage = null,
                ratingCount = null,
                numPages = 300,
                numEditions = 3
            ),
            displayName = "No rating"
        )
    )
}

data class ReadingListItemStatePreview(
    val book: Book,
    val displayName: String
)
