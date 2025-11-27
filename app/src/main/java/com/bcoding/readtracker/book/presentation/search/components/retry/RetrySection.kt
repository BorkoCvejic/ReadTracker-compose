package com.bcoding.readtracker.book.presentation.search.components.retry

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bcoding.readtracker.R
import com.bcoding.readtracker.book.presentation.search.SearchScreenActions
import com.bcoding.readtracker.book.presentation.shared.actions.UiActions
import com.bcoding.readtracker.core.presentation.UiText
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun RetrySection(
    error: UiText,
    onAction: (UiActions) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.appDimensions.dimen16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = error.asString(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen8))
        Button(
            onClick = { onAction(SearchScreenActions.OnLoadMore) }
        ) {
            Text(
                text = stringResource(R.string.search_screen_retry_button_label),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RetrySectionPreview() {
    ReadTrackerTheme {
        Surface {
            RetrySection(
                error = UiText.StringResourceId(R.string.error_no_internet),
                onAction = {}
            )
        }
    }
}
