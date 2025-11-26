package com.bcoding.readtracker.book.presentation.book_details.components.checkbox.checkbox_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.bcoding.readtracker.book.domain.model.ReadingListOverview
import com.bcoding.readtracker.book.presentation.book_details.components.checkbox.checkbox_item.CheckboxItem
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun CheckboxList(
    readingListOverviews: List<ReadingListOverview>,
    selectedLists: Set<Long>,
    onToggle: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = MaterialTheme.appDimensions.dialogHeightMax)
    ) {
        items(
            items = readingListOverviews,
            key = { readingListOverview -> readingListOverview.readingListId }
        ) { readingList ->
            CheckboxItem(
                readingList = readingList,
                isChecked = readingList.readingListId in selectedLists,
                onToggle = { onToggle(readingList.readingListId) }
            )
        }
    }
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
fun CheckboxListPreview(
    @PreviewParameter(CheckboxListPreviewProvider::class) checkboxListStatePreview: CheckboxListStatePreview
) {
    ReadTrackerTheme {
        Surface {
            CheckboxList(
                readingListOverviews = checkboxListStatePreview.readingListOverviews,
                selectedLists = checkboxListStatePreview.selectedLists,
                onToggle = {}
            )
        }
    }
}
