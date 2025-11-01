package com.bcoding.readtracker.domain

import com.bcoding.readtracker.core.domain.DataError
import com.bcoding.readtracker.core.presentation.UiText
import com.bcoding.readtracker.core.presentation.toUiText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.bcoding.readtracker.R

class ErrorMappingTest {
    @Test
    fun `DataError Remote NO_INTERNET maps to UiText StringResourceId`() {
        val error = DataError.Remote.NO_INTERNET
        val uiText = error.toUiText()

        assertTrue(uiText is UiText.StringResourceId)
        assertEquals(R.string.error_no_internet, (uiText as UiText.StringResourceId).resId)
    }

    @Test
    fun `DataError HttpError maps to UiText DynamicString`() {
        val code = 404
        val message = "Not Found"
        val error = DataError.HttpError(code, message)
        val uiText = error.toUiText()

        assertTrue(uiText is UiText.DynamicString)
        assertEquals(message, (uiText as UiText.DynamicString).value)
    }
}