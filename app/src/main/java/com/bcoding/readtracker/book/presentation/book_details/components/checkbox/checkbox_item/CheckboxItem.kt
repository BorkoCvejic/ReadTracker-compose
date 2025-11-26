package com.bcoding.readtracker.book.presentation.book_details.components.checkbox.checkbox_item

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.bcoding.readtracker.book.domain.model.ReadingListOverview
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme

@Composable
fun CheckboxItem(
    readingList: ReadingListOverview,
    isChecked: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onToggle() }
        )
        Text(
            text = readingList.readingListName,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
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
fun CheckboxItemPreview(
    @PreviewParameter(CheckboxPreviewProvider::class) checkboxStatePreview: CheckboxStatePreview
) {
    ReadTrackerTheme {
        Surface {
            CheckboxItem(
                readingList = checkboxStatePreview.readingList,
                isChecked = checkboxStatePreview.isChecked,
                onToggle = {}
            )
        }
    }
}
