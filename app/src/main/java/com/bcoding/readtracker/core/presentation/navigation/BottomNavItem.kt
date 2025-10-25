package com.bcoding.readtracker.core.presentation.navigation

import com.bcoding.readtracker.R
import com.bcoding.readtracker.core.presentation.navigation.Routes.*
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavItem<T>(
    val name: String,
    val icon: Int,
    val route: T
) {
    @Serializable
    data object HomeNavItem: BottomNavItem<HomeGraph>(
        name = "Home",
        icon = R.drawable.ic_search,
        route = HomeGraph
    )

    @Serializable
    data object ProgressTrackerNavItem: BottomNavItem<ProgressTrackerGraph>(
        name = "Progress",
        icon = R.drawable.ic_book,
        route = ProgressTrackerGraph
    )

    @Serializable
    data object FavoritesNavItem: BottomNavItem<FavoritesGraph>(
        name = "Favorites",
        icon = R.drawable.ic_favorite,
        route = FavoritesGraph
    )
}
