package com.bcoding.readtracker.core.presentation.components.read_tracker_dialog

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.bcoding.readtracker.R
import com.bcoding.readtracker.core.presentation.components.read_tracker_text_field.ReadTrackerTextField
import com.bcoding.readtracker.core.presentation.components.read_tracker_text_field.TextFieldError
import com.bcoding.readtracker.core.presentation.components.read_tracker_text_field.validateListName
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun ReadTrackerDialog(
    dialogType: DialogType,
    readingListName: String? = null,
    existingReadingListNames: List<String>,
    onConfirmClick: (String) -> Unit,
    onDismissDialog: () -> Unit,
) {
    var newReadingListName by remember { mutableStateOf(readingListName ?: "") }
    var textFieldError by remember { mutableStateOf<TextFieldError?>(null) }

    AlertDialog(
        title = {
            Text(
                text = stringResource(dialogType.title),
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            when (dialogType) {
                DialogType.RENAME,
                DialogType.CREATE -> {
                    ReadTrackerTextField(
                        value = newReadingListName,
                        onValueChange = { newValue ->
                            newReadingListName = newValue
                            textFieldError = null
                        },
                        error = textFieldError,
                        placeholder = stringResource(dialogType.placeholder),
                        onSubmit = {
                            val validation = validateListName(
                                readingListName = newReadingListName,
                                existingReadingListNames = existingReadingListNames
                            )
                            if (validation == null) {
                                onConfirmClick(newReadingListName.trim())
                                newReadingListName = ""
                            } else {
                                textFieldError = validation
                            }
                        }
                    )
                }
                DialogType.DELETE -> {
                    readingListName?.let { listName -> // never null for DialogType.DELETE
                        Text(
                            text = buildAnnotatedString {
                                val message = stringResource(
                                    R.string.dialog_confirmation_message_delete,
                                    listName
                                )
                                val messageParts = message.split(listName)

                                append(messageParts[0])
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    append(listName)
                                }
                                append(messageParts[1])
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    when (dialogType) {
                        DialogType.RENAME,
                        DialogType.CREATE -> {
                            val validation = validateListName(
                                readingListName = newReadingListName,
                                existingReadingListNames = existingReadingListNames
                            )
                            if (validation == null) {
                                onConfirmClick(newReadingListName.trim())
                                newReadingListName = ""
                            } else {
                                textFieldError = validation
                            }
                        }
                        DialogType.DELETE -> {
                            onConfirmClick(newReadingListName.trim())
                        }
                    }
                }
            ) {
                Text(
                    text = stringResource(dialogType.confirmButtonText),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissDialog()
                }
            ) {
                Text(
                    text = stringResource(R.string.button_label_cancel),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        onDismissRequest = { onDismissDialog() },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
            MaterialTheme.appDimensions.elevationDefault
        )
    )
}

enum class DialogType(
    val placeholder: Int = 0,
    val title: Int,
    val confirmButtonText: Int
) {
    RENAME(
        placeholder = R.string.dialog_rename_text_field_placeholder,
        title = R.string.dialog_rename_title,
        confirmButtonText = R.string.dialog_rename_confirm_button_label
    ),
    CREATE(
        placeholder = R.string.dialog_create_text_field_placeholder,
        title = R.string.dialog_create_title,
        confirmButtonText = R.string.dialog_create_confirm_button_label
    ),
    DELETE(
        title = R.string.dialog_delete_title,
        confirmButtonText = R.string.dialog_delete_confirm_button_label
    )
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReadTrackerDialogPreview(
    @PreviewParameter(ReadTrackerDialogPreviewProvider::class) readTrackerDialogStatePreview: ReadTrackerDialogStatePreview
) {
    ReadTrackerTheme {
        ReadTrackerDialog(
            dialogType = readTrackerDialogStatePreview.dialogType,
            readingListName = readTrackerDialogStatePreview.readingListName,
            existingReadingListNames = emptyList(),
            onConfirmClick = {},
            onDismissDialog = {}
        )
    }
}
