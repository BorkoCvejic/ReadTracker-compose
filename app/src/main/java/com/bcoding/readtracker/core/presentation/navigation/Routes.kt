package com.bcoding.readtracker.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    // Home
    @Serializable
    object HomeGraph: Routes()
    @Serializable
    object Home: Routes()

    // Progress tracker
    @Serializable
    object ProgressTrackerGraph: Routes()
    @Serializable
    object ProgressTracker: Routes()

    // Favorites
    @Serializable
    object FavoritesGraph: Routes()
    @Serializable
    object Favorites: Routes()

    // Shared routes
    @Serializable
    object BookDetails: Routes()
}