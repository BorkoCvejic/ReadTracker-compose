package com.bcoding.readtracker.book.presentation.book_details

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bcoding.readtracker.R
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.presentation.book_details.components.add_to_list_dialog.AddToReadingListDialog
import com.bcoding.readtracker.book.presentation.book_details.components.blurred_image_background.BlurredImageBackground
import com.bcoding.readtracker.book.presentation.book_details.components.book_chip.BookChip
import com.bcoding.readtracker.book.presentation.book_details.components.titled_content.TitledContent
import com.bcoding.readtracker.core.presentation.UiText
import com.bcoding.readtracker.core.presentation.components.PulseAnimation
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmOrange
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun BookDetailsScreenRoot(
    bookDetailsViewModel: BookDetailsViewModel,
    navigateUp: () -> Unit
) {
    val state by bookDetailsViewModel.state.collectAsStateWithLifecycle()
    var dialogBook by remember { mutableStateOf<Book?>(null) }

    LaunchedEffect(Unit) {
        bookDetailsViewModel.events.collect { event ->
            when (event) {
                BookDetailsUiEvents.NavigateBack -> { navigateUp() }
                is BookDetailsUiEvents.ShowAddToListDialog -> {
                    dialogBook = event.book
                }
            }
        }
    }

    dialogBook?.let { book ->
        AddToReadingListDialog(
            readingListOverviews = state.availableReadingLists,
            existingReadingListNames = state.existingReadingListNames,
            selectedReadingListIds = state.selectedListIds,
            onCreateNewList = { readingListName ->
                bookDetailsViewModel.onAction(
                    BookDetailsScreenActions.OnCreateNewReadingListClick(
                        readingListName = readingListName
                    )
                )
            },
            onDismiss = { dialogBook = null },
            onAddToLists = { selectedLists ->
                bookDetailsViewModel.onAction(
                    BookDetailsScreenActions.OnSaveBookToReadingListsClick(
                        book = book,
                        selectedReadingLists = selectedLists
                    )
                )
                dialogBook = null
            }
        )
    }

    BookDetailsScreen(
        isLoadingDescription = state.isLoadingDescription,
        error = state.error,
        selectedBook = state.book,
        onAction = bookDetailsViewModel::onAction
    )
}

@Composable
fun BookDetailsScreen(
    isLoadingDescription: Boolean,
    error: UiText?,
    selectedBook: Book?,
    onAction: (BookDetailsScreenActions) -> Unit
) {
    selectedBook?.let { book ->
        BlurredImageBackground(
            modifier = Modifier.fillMaxSize(),
            imageUrl = book.imageUrl,
            title = book.title,
            onBackClick = { onAction(BookDetailsScreenActions.OnBackClick) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MaterialTheme.appDimensions.dimen16)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.dimen16)
                ) {
                    Column(
                        modifier = Modifier.weight(0.85f)
                    ) {
                        Text(
                            text = book.title,
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen4))
                        Text(
                            text = book.authors.joinToString(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    Icon(
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.appDimensions.dimen8)
                            .clickable { onAction(BookDetailsScreenActions.OnAddToReadingListClick) },
                        painter = painterResource(
                            R.drawable.ic_bookmark
                        ),
                        contentDescription = stringResource(R.string.book_details_screen_content_desc_add_book_to_list),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MaterialTheme.appDimensions.dimen12),
                    thickness = MaterialTheme.appDimensions.dividerThickness
                )
                book.ratingAverage?.let { rating ->
                    TitledContent(
                        title = stringResource(R.string.book_rating)
                    ) {
                        BookChip {
                            Icon(
                                painter = painterResource(R.drawable.ic_star),
                                modifier = Modifier
                                    .padding(MaterialTheme.appDimensions.dimen2)
                                    .size(MaterialTheme.appDimensions.dimen16),
                                tint = WarmOrange,
                                contentDescription = stringResource(R.string.book_rating),
                            )
                            Text(
                                text = "%.1f".format(rating),
                                style = MaterialTheme.typography.bodySmall
                            )
                            book.ratingCount?.let { ratingCnt ->
                                VerticalDivider(
                                    modifier = Modifier
                                        .padding(horizontal = MaterialTheme.appDimensions.dimen8)
                                        .height(MaterialTheme.appDimensions.dividerHeight),
                                    thickness = MaterialTheme.appDimensions.dividerThickness
                                )
                                Text(
                                    text = "$ratingCnt votes",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen8))
                book.firstPublishedYear?.let { year ->
                    TitledContent(
                        title = stringResource(R.string.book_details_screen_label_first_published_year)
                    ) {
                        BookChip {
                            Text(
                                text = year,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen8))
                book.numPages?.let { numOfPages ->
                    TitledContent(
                        title = stringResource(R.string.book_details_screen_label_number_of_pages)
                    ) {
                        BookChip {
                            Text(
                                text = "$numOfPages pages",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen8))
                if (book.languages.isNotEmpty())
                    TitledContent(
                        title = stringResource(R.string.book_details_screen_label_languages),
                        isExpandable = true,
                        isInitiallyExpanded = false
                    ) {
                        FlowRow(
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        ) {
                            book.languages.forEach { language ->
                                BookChip(
                                    modifier = Modifier.padding(all = MaterialTheme.appDimensions.dimen2)
                                ) {
                                    Text(
                                        text = language.uppercase(),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TitledContent(
                        title = stringResource(R.string.book_details_screen_label_synopsis),
                    ) {
                        when {
                            isLoadingDescription ->
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    PulseAnimation()
                                }
                            error != null -> Text(
                                text = error.asString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                            book.description.isEmpty() -> Text(
                                text = stringResource(R.string.book_details_screen_message_description_unavailable),
                                style = MaterialTheme.typography.bodySmall
                            )
                            else -> {
                                Text(
                                    text = book.description,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen8))
            }
        }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.book_details_screen_error_loading),
                style = MaterialTheme.typography.titleMedium
            )
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
private fun BookDetailsScreenPreview(
    @PreviewParameter(BookDetailsScreenPreviewProvider::class) detailsStateProvider: BookDetailsScreenStateProvider
) {
    ReadTrackerTheme {
        Surface {
            BookDetailsScreen(
                isLoadingDescription = detailsStateProvider.isLoadingDescription,
                error = detailsStateProvider.error,
                selectedBook = detailsStateProvider.selectedBook,
                onAction = {}
            )
        }
    }
}
