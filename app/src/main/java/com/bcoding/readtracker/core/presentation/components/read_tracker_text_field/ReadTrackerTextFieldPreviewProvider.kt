package com.bcoding.readtracker.core.presentation.components.read_tracker_text_field

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ReadTrackerTextFieldPreviewProvider: PreviewParameterProvider<ReadTrackerTextFieldStatePreview> {
    override val values: Sequence<ReadTrackerTextFieldStatePreview>
        get() = states.asSequence()

    override fun getDisplayName(index: Int): String? {
        return states[index].displayName
    }

    private val states = listOf(
        ReadTrackerTextFieldStatePreview(
            value = "",
            placeholder = "Enter list name",
            error = null,
            displayName = "Regular text field"
        ),
        ReadTrackerTextFieldStatePreview(
            value = "Thrillers",
            placeholder = "Enter list name",
            error = null,
            displayName = "Regular text field"
        ),
        ReadTrackerTextFieldStatePreview(
            value = "",
            placeholder = "Enter list name",
            error = TextFieldError.EMPTY,
            displayName = "Error - empty field"
        ),
        ReadTrackerTextFieldStatePreview(
            value = "Horrors",
            placeholder = "Enter new name",
            error = TextFieldError.DUPLICATE,
            displayName = "Error - duplicate name"
        )
    )
}


data class ReadTrackerTextFieldStatePreview(
    val value: String,
    val placeholder: String?,
    val error: TextFieldError?,
    val displayName: String
)
