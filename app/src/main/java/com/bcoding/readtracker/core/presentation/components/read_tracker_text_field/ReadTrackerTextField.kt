package com.bcoding.readtracker.core.presentation.components.read_tracker_text_field

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.bcoding.readtracker.R
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme

@Composable
fun ReadTrackerTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String?,
    error: TextFieldError?,
    onSubmit: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            placeholder?.let { textFieldPlaceholder ->
                Text(
                    text = textFieldPlaceholder,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        isError = error != null,
        supportingText = {
            error?.let { textFieldError ->
                Text(
                    text = stringResource(textFieldError.message),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        shape = MaterialTheme.shapes.medium,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
                onSubmit()
            }
        )
    )
}

fun validateListName(readingListName: String, existingReadingListNames: List<String>): TextFieldError? {
    return when {
        readingListName.isBlank() -> TextFieldError.EMPTY
        existingReadingListNames.contains(readingListName.trim()) -> TextFieldError.DUPLICATE
        else -> null
    }
}

enum class TextFieldError(val message: Int) {
    EMPTY(R.string.error_empty_name),
    DUPLICATE(R.string.error_duplicate_list)
}

@Preview(
    name = "Light mode",
    showBackground = true,
    backgroundColor = 0xFFFFFBFE
)
@Preview(
    name = "Dark mode",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ReadTrackerTextFieldPreview(
    @PreviewParameter(ReadTrackerTextFieldPreviewProvider::class) textFieldStatePreview: ReadTrackerTextFieldStatePreview
) {
    ReadTrackerTheme {
        ReadTrackerTextField(
            modifier = Modifier,
            value = textFieldStatePreview.value,
            placeholder = textFieldStatePreview.placeholder,
            error = textFieldStatePreview.error,
            onValueChange = {},
            onSubmit = {}
        )
    }
}
