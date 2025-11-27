package com.bcoding.readtracker.book.presentation.search.components.search_results

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bcoding.readtracker.R
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.presentation.reading_list.components.reading_list_item.ReadingListItem
import com.bcoding.readtracker.book.presentation.search.SearchScreenActions
import com.bcoding.readtracker.book.presentation.search.components.retry.RetrySection
import com.bcoding.readtracker.book.presentation.shared.actions.UiActions
import com.bcoding.readtracker.core.presentation.UiText
import com.bcoding.readtracker.core.presentation.components.PulseAnimation
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun SearchResultsSection(
    books: List<Book>,
    isLoadingMore: Boolean,
    canLoadMore: Boolean,
    error: UiText?,
    onAction: (UiActions) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(error) {
        if (error != null && books.isNotEmpty()) {
            listState.animateScrollToItem(books.size + 1)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.search_screen_label_search_results),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.appDimensions.dimen16)
        )
        LazyColumn(state = listState) {
            // Books
            itemsIndexed(
                items = books,
                key = { _, book -> book.bookId }
            ) { index, book ->
                ReadingListItem(
                    book = book,
                    onAction = { action -> onAction(action) }
                )

                // Load more at 80% shown items, PAGE_SIZE is 10
                if (index >= books.size - 2 && canLoadMore && !isLoadingMore && error == null) {
                    onAction(SearchScreenActions.OnLoadMore)
                }
            }

            // Bottom section with loading or error message with retry button
            item {
                if (isLoadingMore) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        PulseAnimation()
                    }
                } else if (error != null && books.isNotEmpty()) {
                    RetrySection(
                        error = error,
                        onAction = { action -> onAction(action) }
                    )
                }
            }
        }
    }
}
