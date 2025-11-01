package com.bcoding.readtracker.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.bcoding.readtracker.book.presentation.search.SearchScreen
import com.bcoding.readtracker.core.presentation.UiText
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchScreen_displaysErrorText_whenErrorIsPresent() {
        val errorMessage = "Couldn't reach server, please check your internet connection."

        composeTestRule.setContent {
            ReadTrackerTheme {
                SearchScreen(
                    modifier = Modifier.Companion,
                    isLoading = false,
                    error = UiText.DynamicString(errorMessage),
                    books = emptyList(),
                    searchQuery = "Harry Potter",
                    onAction = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }
}
