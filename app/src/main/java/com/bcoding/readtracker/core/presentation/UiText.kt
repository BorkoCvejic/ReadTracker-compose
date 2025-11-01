package com.bcoding.readtracker.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class DynamicString(val value: String): UiText

    data class StringResourceId(
        val resId: Int,
        val args: List<Any> = emptyList(),
    ): UiText

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResourceId -> stringResource(id = resId, formatArgs = args.toTypedArray())
        }
    }
}