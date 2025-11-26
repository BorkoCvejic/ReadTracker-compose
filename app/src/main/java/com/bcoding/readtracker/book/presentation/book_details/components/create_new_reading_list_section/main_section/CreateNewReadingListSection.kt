package com.bcoding.readtracker.book.presentation.book_details.components.create_new_reading_list_section.main_section

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bcoding.readtracker.book.presentation.book_details.components.create_new_reading_list_section.create_new_reading_list_button.CreateNewReadingListButton
import com.bcoding.readtracker.book.presentation.book_details.components.create_new_reading_list_section.create_new_reading_list_field.CreateNewReadingListField
import com.bcoding.readtracker.core.presentation.components.read_tracker_text_field.TextFieldError
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme

@Composable
fun CreateNewReadingListSection(
    isCreatingNewReadingList: Boolean,
    newReadingListName: String,
    textFieldError: TextFieldError?,
    onNameChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onShowCreateField: () -> Unit
) {
    if (isCreatingNewReadingList) {
        CreateNewReadingListField(
            value = newReadingListName,
            error = textFieldError,
            onValueChange = onNameChange,
            onConfirm = onConfirm,
            onCancel = onCancel
        )
    } else {
        CreateNewReadingListButton(
            onClick = onShowCreateField
        )
    }
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CreateNewReadingListSectionPreview() {
    ReadTrackerTheme {
        Surface {
            CreateNewReadingListSection(
                isCreatingNewReadingList = false,
                newReadingListName = "",
                textFieldError = null,
                onNameChange = {},
                onConfirm = {},
                onCancel = {},
                onShowCreateField = {}
            )
        }
    }
}
