package com.bcoding.readtracker.book.presentation.library.components.library_item_dropdown_menu

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcoding.readtracker.R
import com.bcoding.readtracker.core.presentation.components.read_tracker_dialog.DialogType
import com.bcoding.readtracker.core.presentation.components.read_tracker_dialog.ReadTrackerDialog
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme

@Composable
fun LibraryItemDropdownMenu(
    readingListName: String,
    existingReadingListNames: List<String>,
    onDeleteListClick: () -> Unit,
    onRenameListClick: (String) -> Unit
) {
    var expandedMore by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showRenameDialog by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { expandedMore = !expandedMore }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_more),
                contentDescription = stringResource(R.string.library_screen_drop_down_menu_content_desc_more_options)
            )
        }
        DropdownMenu(
            expanded = expandedMore,
            onDismissRequest = { expandedMore = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.library_screen_drop_down_menu_change_list_name),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = stringResource(R.string.library_screen_drop_down_menu_change_list_name)
                    )
                },
                onClick = {
                    showRenameDialog = true
                    expandedMore = false
                }
            )
            HorizontalDivider()
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.library_screen_drop_down_menu_delete_list),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = stringResource(R.string.library_screen_drop_down_menu_delete_list),
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                onClick = {
                    showDeleteDialog = true
                    expandedMore = false
                }
            )
        }
    }

    if (showDeleteDialog) {
        ReadTrackerDialog(
            dialogType = DialogType.DELETE,
            readingListName = readingListName,
            existingReadingListNames = existingReadingListNames,
            onConfirmClick = {
                onDeleteListClick()
                showDeleteDialog = false
            },
            onDismissDialog = {
                showDeleteDialog = false
            }
        )
    }

    if (showRenameDialog) {
        ReadTrackerDialog(
            dialogType = DialogType.RENAME,
            readingListName = readingListName,
            existingReadingListNames = existingReadingListNames,
            onConfirmClick = { newName ->
                onRenameListClick(newName)
                showRenameDialog = false
            },
            onDismissDialog = {
                showRenameDialog = false
            }
        )
    }
}

@Preview(
    name = "Closed - light mode",
    showBackground = true,
    backgroundColor = 0xFFFFFBFE
)
@Preview(
    name = "Closed - dark mode",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun LibraryItemDropdownMenuClosedPreview() {
    ReadTrackerTheme {
        LibraryItemDropdownMenu(
            readingListName = "Thrillers",
            existingReadingListNames = emptyList(),
            onDeleteListClick = {},
            onRenameListClick = {}
        )
    }
}

@Preview(
    name = "Expanded - light mode",
    showBackground = true,
    backgroundColor = 0xFFFFFBFE
)
@Preview(
    name = "Expanded - dark mode",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun LibraryItemDropdownMenuExpandedPreview() { // use interactive mode
    var expanded by remember { mutableStateOf(true) }

    ReadTrackerTheme {
        Box(
            modifier = Modifier.heightIn(200.dp)
        ) {
            IconButton(
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_more),
                    contentDescription = "More options"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Change list name",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = "Change list name"
                        )
                    },
                    onClick = {}
                )
                HorizontalDivider()
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Delete list",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "Delete list",
                            tint = MaterialTheme.colorScheme.error
                        )
                    },
                    onClick = {}
                )
            }
        }
    }
}

@Preview(
    name = "With dialog - light mode",
    showBackground = true,
    backgroundColor = 0xFFFFFBFE
)
@Preview(
    name = "With dialog - dark mode",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun LibraryItemDropdownMenuWithDialogPreview() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        ReadTrackerTheme {
            ReadTrackerDialog(
                dialogType = DialogType.RENAME,
                readingListName = "Classics",
                existingReadingListNames = emptyList(),
                onConfirmClick = { showDialog = false },
                onDismissDialog = { showDialog = false }
            )
        }
    }
}
