package com.bcoding.readtracker.book.presentation.search.components.search_bar

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class SearchBarQueryProvider: PreviewParameterProvider<SearchQueryStatePreview> {
    override val values: Sequence<SearchQueryStatePreview>
        get() = queries.asSequence()

    override fun getDisplayName(index: Int): String? {
        return queries[index].displayName
    }

    private val queries = listOf(
        SearchQueryStatePreview(
            query = "",
            displayName = "Empty"
        ),
        SearchQueryStatePreview(
            query = "Harry Potter",
            displayName = "Regular query"
        ),
        SearchQueryStatePreview(
            query = "Very long query to test the search bar overflow behavior",
            displayName = "Long query"
        )
    )
}

data class SearchQueryStatePreview(
    val query: String,
    val displayName: String
)
