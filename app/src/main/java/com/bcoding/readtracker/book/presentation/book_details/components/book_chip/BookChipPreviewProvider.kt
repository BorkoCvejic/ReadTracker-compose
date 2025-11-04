package com.bcoding.readtracker.book.presentation.book_details.components.book_chip

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.bcoding.readtracker.R

class BookChipPreviewProvider: PreviewParameterProvider<BookChipStatePreview> {
    override val values: Sequence<BookChipStatePreview>
        get() = chips.asSequence()

    override fun getDisplayName(index: Int): String? {
        return chips[index].displayName
    }

    val chips = listOf(
        BookChipStatePreview(
            stringContent = "Rating",
            displayName = "String Content"
        ),
        BookChipStatePreview(
            drawableContent = R.drawable.ic_favorite,
            displayName = "Drawable Content"
        )
    )
}

data class BookChipStatePreview(
    val stringContent: String? = null,
    val drawableContent: Int? = null,
    val displayName: String
)