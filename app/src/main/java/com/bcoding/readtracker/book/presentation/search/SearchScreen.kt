package com.bcoding.readtracker.book.presentation.search

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bcoding.readtracker.R
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.presentation.search.components.search_bar.ReadTrackerSearchBar
import com.bcoding.readtracker.book.presentation.search.components.search_results.SearchResultsSection
import com.bcoding.readtracker.book.presentation.shared.actions.UiActions
import com.bcoding.readtracker.core.presentation.UiText
import com.bcoding.readtracker.core.presentation.components.PulseAnimation
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun SearchScreenRoot(
    modifier: Modifier,
    searchViewModel: SearchViewModel,
    showDetails: (Book) -> Unit
) {
    val searchScreenState by searchViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        searchViewModel.events.collect { events ->
            when (events) {
                is SearchScreenUiEvents.NavigateToBookDetails -> { showDetails(events.book) }
            }
        }
    }

    SearchScreen(
        modifier = modifier,
        isLoading = searchScreenState.isLoading,
        isLoadingMore = searchScreenState.isLoadingMore,
        canLoadMore = searchScreenState.canLoadMore,
        searchQuery = searchScreenState.searchQuery,
        books = searchScreenState.books,
        error = searchScreenState.error,
        onAction = searchViewModel::onAction
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier,
    isLoading: Boolean,
    isLoadingMore: Boolean,
    canLoadMore: Boolean,
    searchQuery: String,
    books: List<Book>,
    error: UiText?,
    onAction: (UiActions) -> Unit
) {
    // used for clearing focus when clicking outside of search bar
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            }
    ) {
        Column(
            modifier = modifier
                .padding(MaterialTheme.appDimensions.dimen16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReadTrackerSearchBar(
                searchQuery = searchQuery,
                onQueryChange = { query ->
                    onAction(SearchScreenActions.OnSearchQueryChange(query = query))
                }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen24))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> PulseAnimation()
                    error != null && books.isEmpty()-> {
                        Text(
                            text = error.asString(),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                    books.isEmpty() -> {
                        Text(
                            text = stringResource(R.string.search_screen_message_no_search_results),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                    else -> {
                        SearchResultsSection(
                            books = books,
                            isLoadingMore = isLoadingMore,
                            canLoadMore = canLoadMore,
                            error = error,
                            onAction = onAction
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Light mode",
    showBackground = true
)
@Preview(
    name = "Dark mode",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SearchScreenPreview(
    @PreviewParameter(SearchScreenPreviewProvider::class) statePreview: SearchScreenStatePreview,
) {
    ReadTrackerTheme {
        SearchScreen(
            modifier = Modifier,
            isLoading = statePreview.state.isLoading,
            isLoadingMore = statePreview.state.isLoadingMore,
            canLoadMore = statePreview.state.canLoadMore,
            error = statePreview.state.error,
            books = statePreview.state.books,
            searchQuery = statePreview.state.searchQuery,
            onAction = {}
        )
    }
}
