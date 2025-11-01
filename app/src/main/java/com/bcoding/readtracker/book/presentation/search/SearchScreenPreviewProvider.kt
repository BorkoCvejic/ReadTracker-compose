package com.bcoding.readtracker.book.presentation.search

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.core.presentation.UiText

class SearchScreenPreviewProvider: PreviewParameterProvider<SearchScreenStatePreview> {
    override val values: Sequence<SearchScreenStatePreview>
        get() = states.asSequence()

    override fun getDisplayName(index: Int): String? {
        return states[index].displayName
    }

    private val books = (1..10).map {
        Book(
            id = it.toString(),
            title = "Book $it",
            imageUrl = "https://test.com",
            authors = listOf("Borko Cvejic"),
            description = "Description $it",
            languages = emptyList(),
            firstPublishedYear = null,
            ratingAverage = 4.6757,
            ratingCount = 5,
            numPages = 100,
            numEditions = 3
        )
    }

    val states = listOf(
        SearchScreenStatePreview(
            state = SearchState(
                isLoading = false,
                error = null,
                books = books,
                searchQuery = "Harry Potter"
            ),
            displayName = "Results"
        ),
        SearchScreenStatePreview(
            state = SearchState(
                isLoading = false,
                error = null,
                books = emptyList(),
                searchQuery = "Some unknown book"
            ),
            displayName = "Empty"
        ),
        SearchScreenStatePreview(
            state = SearchState(
                isLoading = true,
                error = null,
                books = emptyList(),
                searchQuery = "Harry Potter"
            ),
            displayName = "Loading"
        ),
        SearchScreenStatePreview(
            state = SearchState(
                isLoading = false,
                error = UiText.DynamicString("Something went wrong."),
                books = emptyList(),
                searchQuery = "Harry Potter"
            ),
            displayName = "Error"
        ),
    )
}

data class SearchScreenStatePreview(
    val state: SearchState,
    val displayName: String
)
