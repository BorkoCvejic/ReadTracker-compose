package com.bcoding.readtracker.book.presentation.library

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bcoding.readtracker.R
import com.bcoding.readtracker.book.domain.model.ReadingListOverview
import com.bcoding.readtracker.core.presentation.components.read_tracker_dialog.DialogType
import com.bcoding.readtracker.core.presentation.components.read_tracker_dialog.ReadTrackerDialog
import com.bcoding.readtracker.book.presentation.library.components.library_item.LibraryItem
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun LibraryScreenRoot(
    modifier: Modifier,
    libraryViewModel: LibraryViewModel,
    showReadingList: (ReadingListOverview) -> Unit
) {
    val state by libraryViewModel.state.collectAsStateWithLifecycle()
    var showCreateNewListDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        libraryViewModel.events.collect { event ->
            when (event) {
                is LibraryScreenUiEvents.NavigateToBookDetails -> showReadingList(event.readingListOverview)
                LibraryScreenUiEvents.ShowCreateNewReadingListDialog -> {
                    showCreateNewListDialog = true
                }
            }
        }
    }

    if (showCreateNewListDialog) {
        ReadTrackerDialog(
            dialogType = DialogType.CREATE,
            existingReadingListNames = state.existingReadingListNames,
            onConfirmClick = { listName ->
                showCreateNewListDialog = false
                libraryViewModel.onAction(LibraryScreenActions.OnCreateNewReadingListDialogClick(listName))
            },
            onDismissDialog = {
                showCreateNewListDialog = false
            }
        )
    }

    LibraryScreen(
        modifier = modifier,
        readingLists = state.readingLists,
        existingReadingListNames = state.existingReadingListNames,
        onAction = libraryViewModel::onAction
    )
}

@Composable
fun LibraryScreen(
    modifier: Modifier,
    readingLists: List<ReadingListOverview>,
    existingReadingListNames: List<String>,
    onAction: (LibraryScreenActions) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            // modifier with innerPadding from Scaffold is needed for FloatingActionButton
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(horizontal = MaterialTheme.appDimensions.dimen16),
            ) {
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen8))
                Text(
                    text = stringResource(R.string.library_screen_title),
                    style = MaterialTheme.typography.displayMedium
                )
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen16))
                if (readingLists.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = readingLists,
                            key = { readingList -> readingList.readingListId }
                        ) { readingList ->
                            LibraryItem(
                                readingList = readingList,
                                existingReadingListNames = existingReadingListNames,
                                onAction = { action -> onAction(action) }
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.library_screen_message_no_saved_lists),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(MaterialTheme.appDimensions.dimen16),
                shape = MaterialTheme.shapes.medium,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    onAction(LibraryScreenActions.OnCreateNewReadingListFABClick)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.library_screen_fab_content_desc_add_new_list)
                )
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
private fun LibraryScreenPreview(
    @PreviewParameter(LibraryScreenPreviewProvider::class) statePreview: LibraryScreenStatePreview
) {
    ReadTrackerTheme {
        LibraryScreen(
            modifier = Modifier,
            readingLists = statePreview.state.readingLists,
            existingReadingListNames = statePreview.state.existingReadingListNames,
            onAction = {}
        )
    }
}
