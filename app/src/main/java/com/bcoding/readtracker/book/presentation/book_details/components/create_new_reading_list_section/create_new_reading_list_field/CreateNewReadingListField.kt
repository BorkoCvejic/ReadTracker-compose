package com.bcoding.readtracker.book.presentation.book_details.components.create_new_reading_list_section.create_new_reading_list_field

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bcoding.readtracker.R
import com.bcoding.readtracker.core.presentation.components.read_tracker_dialog.DialogType
import com.bcoding.readtracker.core.presentation.components.read_tracker_text_field.ReadTrackerTextField
import com.bcoding.readtracker.core.presentation.components.read_tracker_text_field.TextFieldError
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun CreateNewReadingListField(
    value: String,
    error: TextFieldError?,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        ReadTrackerTextField(
            modifier = Modifier.weight(0.7f),
            value = value,
            onValueChange = onValueChange,
            error = error,
            placeholder = stringResource(DialogType.CREATE.placeholder),
            onSubmit = onConfirm
        )
        IconButton(
            modifier = Modifier
                .padding(top = MaterialTheme.appDimensions.dimen4)
                .weight(0.15f),
            onClick = onConfirm
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_check),
                contentDescription = stringResource(R.string.add_to_reading_list_dialog_content_desc_confirm),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(
            modifier = Modifier
                .padding(top = MaterialTheme.appDimensions.dimen4)
                .weight(0.15f),
            onClick = onCancel
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = stringResource(R.string.button_label_cancel),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CreateNewReadingListFieldPreview() {
    ReadTrackerTheme {
        Surface {
            CreateNewReadingListField(
                value = "Thrillers",
                error = null,
                onValueChange = {},
                onConfirm = {},
                onCancel = {}
            )
        }
    }
}
