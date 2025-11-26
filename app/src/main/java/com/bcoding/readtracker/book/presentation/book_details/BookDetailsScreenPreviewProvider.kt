package com.bcoding.readtracker.book.presentation.book_details

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bcoding.readtracker.R
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.core.presentation.UiText

class BookDetailsScreenPreviewProvider: PreviewParameterProvider<BookDetailsScreenStateProvider> {
    override val values: Sequence<BookDetailsScreenStateProvider>
        get() = states.asSequence()

    override fun getDisplayName(index: Int): String? {
        return states[index].displayName
    }

    val states = listOf(
        BookDetailsScreenStateProvider(
            isLoadingDescription = false,
            selectedBook = Book(
                bookId = "1",
                title = "Harry Potter",
                imageUrl = "https://test.com",
                authors = listOf("JK Rowling"),
                description = "Book description",
                languages = listOf("ENG", "ESP"),
                firstPublishedYear = null,
                ratingAverage = 4.6757,
                ratingCount = 5,
                numPages = 100,
                numEditions = 3
            ),
            error = null,
            displayName = "Regular"
        ),
        BookDetailsScreenStateProvider(
            isLoadingDescription = false,
            selectedBook = Book(
                bookId = "1",
                title = "Harry Potter",
                imageUrl = "https://test.com",
                authors = listOf("JK Rowling"),
                description = "",
                languages = listOf("ENG", "ESP"),
                firstPublishedYear = null,
                ratingAverage = 4.6757,
                ratingCount = 5,
                numPages = 100,
                numEditions = 3
            ),
            error = null,
            displayName = "No description"
        ),
        BookDetailsScreenStateProvider(
            isLoadingDescription = false,
            selectedBook = Book(
                bookId = "1",
                title = "Harry Potter",
                imageUrl = "https://test.com",
                authors = listOf("JK Rowling"),
                description = "",
                languages = emptyList(),
                firstPublishedYear = null,
                ratingAverage = 4.6757,
                ratingCount = 5,
                numPages = 100,
                numEditions = 3
            ),
            error = null,
            displayName = "No languages"
        ),
        BookDetailsScreenStateProvider(
            isLoadingDescription = false,
            selectedBook = Book(
                bookId = "1",
                title = "Harry Potter and the Philosopher's Stone",
                imageUrl = "https://test.com",
                authors = listOf("JK Rowling"),
                description = "Book description",
                languages = listOf("ENG", "ESP"),
                firstPublishedYear = null,
                ratingAverage = 4.6757,
                ratingCount = 5,
                numPages = 100,
                numEditions = 3
            ),
            error = UiText.StringResourceId(R.string.error_no_internet),
            displayName = "Error"
        ),
        BookDetailsScreenStateProvider(
            isLoadingDescription = true,
            selectedBook = Book(
                bookId = "1",
                title = "Harry Potter and the Philosopher's Stone",
                imageUrl = "https://test.com",
                authors = listOf("JK Rowling"),
                description = "Book description",
                languages = listOf("ENG", "ESP"),
                firstPublishedYear = null,
                ratingAverage = 4.6757,
                ratingCount = 5,
                numPages = 100,
                numEditions = 3
            ),
            error = UiText.StringResourceId(R.string.error_no_internet),
            displayName = "Loading"
        ),
        BookDetailsScreenStateProvider(
            isLoadingDescription = false,
            selectedBook = null,
            error = null,
            displayName = "No book"
        ),
    )
}

data class BookDetailsScreenStateProvider(
    val isLoadingDescription: Boolean,
    val error: UiText?,
    val selectedBook: Book?,
    val displayName: String
)
