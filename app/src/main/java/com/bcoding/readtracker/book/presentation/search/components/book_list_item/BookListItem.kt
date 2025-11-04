package com.bcoding.readtracker.book.presentation.search.components.book_list_item

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.bcoding.readtracker.R
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.presentation.search.SearchScreenActions
import com.bcoding.readtracker.core.presentation.components.PulseAnimation
import com.bcoding.readtracker.core.presentation.theme.AppColors.WarmOrange
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun BookListItem(
    modifier: Modifier = Modifier,
    book: Book,
    onAction: (SearchScreenActions.OnBookClick) -> Unit
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .height(MaterialTheme.appDimensions.dimen140)
            .padding(bottom = MaterialTheme.appDimensions.dimen24),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        onClick = {
            onAction(SearchScreenActions.OnBookClick(book = book))
        }
    ) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.appDimensions.dimen16)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.dimen16)
        ) {
            Box(
                modifier = Modifier
                    .height(MaterialTheme.appDimensions.dimen100)
                    .width(MaterialTheme.appDimensions.dimen60),
                contentAlignment = Alignment.Center
            ) {
                val painter = rememberAsyncImagePainter(book.imageUrl)
                val painterState by painter.state.collectAsStateWithLifecycle()

                when (painterState) {
                    is AsyncImagePainter.State.Empty,
                    is AsyncImagePainter.State.Loading -> {
                        PulseAnimation()
                    }
                    is AsyncImagePainter.State.Error -> {
                        Image(
                            painter = painterResource(R.drawable.ic_error_book),
                            contentDescription = stringResource(
                                R.string.search_screen_book_cover,
                                book.title
                            )
                        )
                    }
                    is AsyncImagePainter.State.Success -> {
                        Image(
                            painter = painter,
                            contentDescription = stringResource(
                                R.string.search_screen_book_cover,
                                book.title
                            )
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = MaterialTheme.appDimensions.dimen4)
                )
                book.authors.firstOrNull()?.let { authorName ->
                    Text(
                        text = authorName,
                        style = MaterialTheme.typography.bodyMedium ,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                book.ratingAverage?.let { ratingAvg ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_star),
                            modifier = Modifier
                                .padding(MaterialTheme.appDimensions.dimen2)
                                .size(MaterialTheme.appDimensions.dimen16),
                            tint = WarmOrange,
                            contentDescription = stringResource(R.string.book_rating),
                        )
                        Text(
                            text = "%.1f".format(ratingAvg),
                            style = MaterialTheme.typography.bodySmall
                        )
                        book.ratingCount?.let { ratingCnt ->
                            VerticalDivider(
                                modifier = Modifier
                                    .padding(horizontal = MaterialTheme.appDimensions.dimen8)
                                    .height(MaterialTheme.appDimensions.dividerHeight),
                                thickness = MaterialTheme.appDimensions.dividerThickness
                            )
                            Text(
                                text = "$ratingCnt votes",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier
                    .size(MaterialTheme.appDimensions.bookItemIcon),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookListItemPreview(
    @PreviewParameter(BookListItemProvider::class) bookStatePreview: BookListItemStatePreview
) {
    ReadTrackerTheme {
        BookListItem(
            book = bookStatePreview.book,
            onAction = {}
        )
    }
}
