package com.bcoding.readtracker.book.presentation.reading_list

import com.bcoding.readtracker.book.presentation.shared.actions.UiActions

sealed interface ReadingListScreenActions: UiActions {
    object OnLoadMore: ReadingListScreenActions
}
