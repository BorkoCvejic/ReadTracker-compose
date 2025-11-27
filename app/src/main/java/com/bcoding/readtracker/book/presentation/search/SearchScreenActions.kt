package com.bcoding.readtracker.book.presentation.search

import com.bcoding.readtracker.book.presentation.shared.actions.UiActions

sealed interface SearchScreenActions: UiActions {
    data class OnSearchQueryChange(val query: String) : SearchScreenActions
    object OnLoadMore : SearchScreenActions
}
