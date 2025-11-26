package com.bcoding.readtracker.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    // Search
    @Serializable
    object SearchGraph: Routes()
    @Serializable
    object Search: Routes()

    // Progress tracker
    @Serializable
    object ProgressTrackerGraph: Routes()
    @Serializable
    object ProgressTracker: Routes()

    // Library
    @Serializable
    object LibraryGraph: Routes()
    @Serializable
    object Library: Routes()

    // Reading list
    @Serializable
    data class ReadingList(val readingListId: Long, val readingListName: String): Routes()

    // Shared routes
    @Serializable
    object BookDetails: Routes()
}