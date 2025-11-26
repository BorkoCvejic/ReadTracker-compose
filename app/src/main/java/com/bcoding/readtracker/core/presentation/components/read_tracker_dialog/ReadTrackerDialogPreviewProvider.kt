package com.bcoding.readtracker.core.presentation.components.read_tracker_dialog

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ReadTrackerDialogPreviewProvider: PreviewParameterProvider<ReadTrackerDialogStatePreview> {
    override val values: Sequence<ReadTrackerDialogStatePreview>
        get() = states.asSequence()

    override fun getDisplayName(index: Int): String? {
        return states[index].displayName
    }

    private val states = listOf(
        ReadTrackerDialogStatePreview(
            dialogType = DialogType.CREATE,
            readingListName = null,
            displayName = "Create"
        ),
        ReadTrackerDialogStatePreview(
            dialogType = DialogType.RENAME,
            readingListName = null,
            displayName = "Rename"
        ),
        ReadTrackerDialogStatePreview(
            dialogType = DialogType.DELETE,
            readingListName = "Classics",
            displayName = "Delete"
        )
    )
}

data class ReadTrackerDialogStatePreview(
    val dialogType: DialogType,
    val readingListName: String? = null,
    val displayName: String
)
