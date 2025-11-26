package com.bcoding.readtracker.book.presentation.book_details.components.add_to_list_dialog

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.bcoding.readtracker.R
import com.bcoding.readtracker.book.domain.model.ReadingListOverview
import com.bcoding.readtracker.book.presentation.book_details.components.checkbox.checkbox_list.CheckboxList
import com.bcoding.readtracker.book.presentation.book_details.components.create_new_reading_list_section.main_section.CreateNewReadingListSection
import com.bcoding.readtracker.core.presentation.components.read_tracker_text_field.TextFieldError
import com.bcoding.readtracker.core.presentation.components.read_tracker_text_field.validateListName
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions
import kotlin.Long
import kotlin.String
import kotlin.Unit
import kotlin.collections.List

@Composable
fun AddToReadingListDialog(
    readingListOverviews: List<ReadingListOverview>,
    existingReadingListNames: List<String>,
    selectedReadingListIds: List<Long>,
    onCreateNewList: (String) -> Unit,
    onDismiss: () -> Unit,
    onAddToLists: (selected: List<Long>) -> Unit
) {
    var selectedReadingLists by remember { mutableStateOf(emptySet<Long>()) }
    var isCreatingNewReadingList by remember { mutableStateOf(false)}
    var newReadingListName by remember { mutableStateOf("") }
    var textFieldError by remember { mutableStateOf<TextFieldError?>(null) }

    LaunchedEffect(selectedReadingListIds) {
        selectedReadingLists = selectedReadingListIds.toSet()
    }

    fun handleCreateReadingList() {
        val validation = validateListName(
            readingListName = newReadingListName,
            existingReadingListNames = existingReadingListNames
        )
        if (validation == null) {
            onCreateNewList(newReadingListName.trim())
            newReadingListName = ""
            isCreatingNewReadingList = false
        } else {
            textFieldError = validation
        }
    }

    AlertDialog(
        title = {
            Text(
                text = stringResource(R.string.add_to_reading_list_dialog_title),
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Column {
                CheckboxList(
                    readingListOverviews = readingListOverviews,
                    selectedLists = selectedReadingLists,
                    onToggle = { readingListId ->
                        selectedReadingLists = if (readingListId in selectedReadingLists) {
                            selectedReadingLists - readingListId
                        } else {
                            selectedReadingLists + readingListId
                        }
                    }
                )
                Spacer(Modifier.height(MaterialTheme.appDimensions.dimen16))
                CreateNewReadingListSection(
                    isCreatingNewReadingList = isCreatingNewReadingList,
                    newReadingListName = newReadingListName,
                    textFieldError = textFieldError,
                    onNameChange = { newValue ->
                        newReadingListName = newValue
                        textFieldError = null
                    },
                    onConfirm = { handleCreateReadingList() },
                    onCancel = {
                        newReadingListName = ""
                        isCreatingNewReadingList = false
                    },
                    onShowCreateField = { isCreatingNewReadingList = true }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAddToLists(selectedReadingLists.toList())
                }
            ) {
                Text(
                    text = stringResource(R.string.button_label_save),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = stringResource(R.string.button_label_cancel),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        onDismissRequest = { onDismiss() },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
            MaterialTheme.appDimensions.elevationSmall
        )
    )
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ReadingListItemPreview(  // use interactive mode
    @PreviewParameter(AddToReadingListDialogPreviewProvider::class) dialogStatePreview: AddToReadingListDialogStatePreview
) {
    ReadTrackerTheme {
        AddToReadingListDialog(
            readingListOverviews = dialogStatePreview.readingListOverviews,
            existingReadingListNames = emptyList(),
            selectedReadingListIds = dialogStatePreview.selectedListIds,
            onCreateNewList = {},
            onDismiss = {},
            onAddToLists = {}
        )
    }
}
