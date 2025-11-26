package com.bcoding.readtracker.book.presentation.book_details.components.checkbox.checkbox_item

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bcoding.readtracker.book.domain.model.ReadingListOverview

class CheckboxPreviewProvider: PreviewParameterProvider<CheckboxStatePreview> {
    override val values: Sequence<CheckboxStatePreview>
        get() = states.asSequence()

    override fun getDisplayName(index: Int): String? {
        return states[index].displayName
    }

    private val states = listOf(
        CheckboxStatePreview(
            readingList = ReadingListOverview(
                readingListId = 1,
                readingListName = "Classics",
                bookCount = 10
            ),
            isChecked = false,
            displayName = "Unchecked"
        ),
        CheckboxStatePreview(
            readingList = ReadingListOverview(
                readingListId = 1,
                readingListName = "Classics",
                bookCount = 10
            ),
            isChecked = true,
            displayName = "Checked"
        )
    )
}

data class CheckboxStatePreview(
    val readingList: ReadingListOverview,
    val isChecked: Boolean,
    val displayName: String
)
