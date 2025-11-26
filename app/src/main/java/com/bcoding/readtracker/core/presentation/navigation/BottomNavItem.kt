package com.bcoding.readtracker.core.presentation.navigation

import com.bcoding.readtracker.R
import com.bcoding.readtracker.core.presentation.navigation.Routes.*
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavItem<T>(
    val name: Int,
    val icon: Int,
    val route: T
) {
    @Serializable
    data object SearchNavItem: BottomNavItem<SearchGraph>(
        name = R.string.nav_item_search_label,
        icon = R.drawable.ic_search,
        route = SearchGraph
    )

    @Serializable
    data object ProgressTrackerNavItem: BottomNavItem<ProgressTrackerGraph>(
        name = R.string.nav_item_search_label,
        icon = R.drawable.ic_book,
        route = ProgressTrackerGraph
    )

    @Serializable
    data object LibraryNavItem: BottomNavItem<LibraryGraph>(
        name = R.string.nav_item_library_label,
        icon = R.drawable.ic_bookmark_stack,
        route = LibraryGraph
    )
}
