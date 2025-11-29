package com.bcoding.readtracker.book.presentation.library.components.library_item

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.bcoding.readtracker.R
import com.bcoding.readtracker.book.domain.model.ReadingListOverview
import com.bcoding.readtracker.book.presentation.library.LibraryScreenActions
import com.bcoding.readtracker.book.presentation.library.components.library_item_dropdown_menu.LibraryItemDropdownMenu
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun LibraryItem(
    readingList: ReadingListOverview,
    existingReadingListNames: List<String>,
    onAction: (LibraryScreenActions) -> Unit
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .height(MaterialTheme.appDimensions.dimen80)
            .padding(
                top = MaterialTheme.appDimensions.dimen8,
                bottom = MaterialTheme.appDimensions.dimen16
            ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = MaterialTheme.appDimensions.elevationDefault
        ),
        onClick = {
            onAction(
                LibraryScreenActions.OnReadingListClick(
                    readingList = readingList
                )
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = MaterialTheme.appDimensions.dimen16,
                    end = MaterialTheme.appDimensions.dimen4
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = readingList.readingListName,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.appDimensions.dimen8))
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.dimen4),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = pluralStringResource(
                        R.plurals.library_screen_books_count,
                        readingList.bookCount,
                        readingList.bookCount
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
                LibraryItemDropdownMenu(
                    readingListName = readingList.readingListName,
                    existingReadingListNames = existingReadingListNames,
                    onDeleteListClick = {
                        onAction(
                            LibraryScreenActions.OnDeleteReadingListClick(
                                readingListId = readingList.readingListId
                            )
                        )
                    },
                    onRenameListClick = { newReadingListName ->
                        onAction(
                            LibraryScreenActions.OnRenameReadingListClick(
                                readingListId = readingList.readingListId,
                                newReadingListName = newReadingListName
                            )
                        )
                    }
                )
            }
        }
    }
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LibraryItemPreview(
    @PreviewParameter(LibraryItemPreviewProvider ::class) libraryItemStatePreview: LibraryItemStatePreview
) {
    ReadTrackerTheme {
        LibraryItem(
            readingList = libraryItemStatePreview.readingList,
            existingReadingListNames = emptyList(),
            onAction = {}
        )
    }
}
