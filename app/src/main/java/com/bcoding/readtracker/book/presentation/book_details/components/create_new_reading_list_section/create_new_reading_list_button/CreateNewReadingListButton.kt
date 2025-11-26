package com.bcoding.readtracker.book.presentation.book_details.components.create_new_reading_list_section.create_new_reading_list_button

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bcoding.readtracker.R
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun CreateNewReadingListButton(
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen4))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(
            MaterialTheme.appDimensions.dimen4
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = stringResource(R.string.add_to_reading_list_dialog_label_create_new_list),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.add_to_reading_list_dialog_label_create_new_list),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CreateNewReadingListButtonPreview() {
    ReadTrackerTheme {
        Surface {
            CreateNewReadingListButton {}
        }
    }
}
