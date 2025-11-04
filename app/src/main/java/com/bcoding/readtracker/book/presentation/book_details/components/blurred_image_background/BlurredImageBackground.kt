package com.bcoding.readtracker.book.presentation.book_details.components.blurred_image_background

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.bcoding.readtracker.R
import com.bcoding.readtracker.core.presentation.components.PulseAnimation
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun BlurredImageBackground(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    title: String,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                val painter = rememberAsyncImagePainter(imageUrl)
                val painterState by painter.state.collectAsStateWithLifecycle()

                when (painterState) {
                    is AsyncImagePainter.State.Success -> {
                        Image(
                            painter = painter,
                            contentDescription = stringResource(
                                R.string.search_screen_book_cover,
                                title
                            ),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(MaterialTheme.appDimensions.imageBlur)
                        )
                    }
                    else -> {}
                }
            }
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                content()
            }
        }
        IconButton(
            onClick = { onBackClick() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = MaterialTheme.appDimensions.dimen32,
                    start = MaterialTheme.appDimensions.dimen16
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = stringResource(R.string.book_details_screen_go_back),
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.appDimensions.dimen60)
        ) {
            ElevatedCard(
                modifier = Modifier
                    .height(MaterialTheme.appDimensions.elevatedImageCardHeight)
                    .fillMaxWidth(0.55f)
                    .aspectRatio(2 / 3f),
                shape = MaterialTheme.shapes.small,
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = MaterialTheme.appDimensions.defaultElevation
                )
            ) {
                val painter = rememberAsyncImagePainter(imageUrl)
                val painterState by painter.state.collectAsStateWithLifecycle()

                when (painterState) {
                    is AsyncImagePainter.State.Empty,
                    is AsyncImagePainter.State.Loading -> {
                        PulseAnimation()
                    }
                    is AsyncImagePainter.State.Error -> {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(R.drawable.ic_error_book),
                            contentDescription = stringResource(
                                R.string.search_screen_book_cover,
                                title
                            )
                        )
                    }
                    is AsyncImagePainter.State.Success -> {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painter,
                            contentDescription = stringResource(
                                R.string.search_screen_book_cover,
                                title
                            ),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }
        }
    }
}
