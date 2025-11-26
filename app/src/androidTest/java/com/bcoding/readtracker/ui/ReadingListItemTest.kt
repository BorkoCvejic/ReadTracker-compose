package com.bcoding.readtracker.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.presentation.reading_list.components.reading_list_item.ReadingListItem
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ReadingListItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun clickOnBookItem_callsOnBookClickAction() {
        var clickedBookId: String? = null
        val mockedBook = Book(
            bookId = "42",
            title = "Harry Potter and the Sorcerer's Stone",
            imageUrl = "https://example.com/image.jpg",
            authors = listOf("JK Rowling"),
            languages = listOf("eng"),
            numEditions = 3,
            description = "Description",
            firstPublishedYear = "2002",
            ratingAverage = 4.45,
            ratingCount = 100,
            numPages = 300
        )

        composeTestRule.setContent {
            ReadingListItem(
                book = mockedBook,
                onAction = { action ->
                    clickedBookId = action.book.bookId
                }
            )
        }

        composeTestRule.onNodeWithText("Harry Potter and the Sorcerer's Stone").performClick()

        assertEquals("42", clickedBookId)
    }
}
