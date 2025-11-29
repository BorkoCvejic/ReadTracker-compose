package com.bcoding.readtracker.book.presentation.reading_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bcoding.readtracker.R
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.presentation.reading_list.components.reading_list_item.ReadingListItem
import com.bcoding.readtracker.book.presentation.shared.actions.UiActions
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun ReadingListScreenRoot(
    modifier: Modifier = Modifier,
    readingListViewModel: ReadingListViewModel,
    readingListName: String,
    navigateUp: () -> Unit,
    showDetails: (Book) -> Unit
) {
    val state by readingListViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        readingListViewModel.events.collect { events ->
            when (events) {
                is ReadingListScreenUiEvents.NavigateToBookDetails -> { showDetails(events.book) }
            }
        }
    }

    ReadingListScreen(
        modifier = modifier,
        readingListName = readingListName,
        savedBooks = state.savedBooks,
        hasMore = state.hasMore,
        isLoadingMore = state.isLoadingMore,
        onBackClick = { navigateUp() },
        onAction = readingListViewModel::onAction
    )
}

@Composable
fun ReadingListScreen(
    modifier: Modifier,
    readingListName: String,
    savedBooks: List<Book>,
    hasMore: Boolean,
    isLoadingMore: Boolean,
    onBackClick: () -> Unit,
    onAction: (UiActions) -> Unit
) {
    Surface(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.appDimensions.dimen24,
                        start = MaterialTheme.appDimensions.dimen8,
                        end = MaterialTheme.appDimensions.dimen24,
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onBackClick() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = stringResource(R.string.button_go_back),
                    )
                }
                Text(
                    text = readingListName,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.appDimensions.dimen16)) {
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen16))
                if (savedBooks.isNotEmpty()) {
                    LazyColumn {
                        itemsIndexed(
                            items = savedBooks,
                            key = { _, book -> book.bookId },
                        ) { index, book ->
                            ReadingListItem(
                                book = book,
                                onAction = { action -> onAction(action) }
                            )

                            // Load more at 80% shown items, PAGE_SIZE is 10
                            if (index >= savedBooks.size - 2 && hasMore && !isLoadingMore) {
                                onAction(ReadingListScreenActions.OnLoadMore)
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.reading_list_screen_message_empty_list),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
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
fun ReadingListScreenPreview(
    @PreviewParameter(ReadingListScreenPreviewProvider::class) statePreview: ReadingListScreenPreview
) {
    ReadTrackerTheme {
        ReadingListScreen(
            modifier = Modifier,
            readingListName = statePreview.readingListName,
            savedBooks = statePreview.state.savedBooks,
            hasMore = statePreview.state.hasMore,
            isLoadingMore = statePreview.state.isLoadingMore,
            onBackClick = {},
            onAction = {}
        )
    }
}
