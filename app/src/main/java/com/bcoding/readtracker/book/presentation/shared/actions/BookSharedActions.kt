package com.bcoding.readtracker.book.presentation.shared.actions

import com.bcoding.readtracker.book.domain.model.Book

interface BookSharedActions: UiActions {
    data class OnBookClick(val book: Book): BookSharedActions
}
