package com.bcoding.readtracker.book.presentation.reading_list

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bcoding.readtracker.book.domain.model.Book

class ReadingListScreenPreviewProvider: PreviewParameterProvider<ReadingListScreenPreview> {
    override val values: Sequence<ReadingListScreenPreview>
        get() = states.asSequence()

    override fun getDisplayName(index: Int): String? {
        return states[index].displayName
    }

    private val books = (1..10).map { bookId ->
        Book(
            bookId = bookId.toString(),
            title = "Book $bookId",
            imageUrl = "https://test.com",
            authors = listOf("Borko Cvejic"),
            description = "Description $bookId",
            languages = emptyList(),
            firstPublishedYear = null,
            ratingAverage = 4.6757,
            ratingCount = 5,
            numPages = 100,
            numEditions = 3
        )
    }

    val states = listOf(
        ReadingListScreenPreview(
            state = ReadingListState(
                savedBooks = books
            ),
            readingListName = "Thrillers",
            displayName = "A lot of lists"
        ),
        ReadingListScreenPreview(
            state = ReadingListState(
                savedBooks = emptyList()
            ),
            readingListName = "Very long title for reading list for testing purposes",
            displayName = "Empty with long title"
        )
    )
}

data class ReadingListScreenPreview(
    val state: ReadingListState,
    val readingListName: String,
    val displayName: String
)
