package com.bcoding.readtracker.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
private fun NavController.isOnAnyRoute(vararg routes: Routes): Boolean {
    val currentDestination = currentBackStackEntryAsState().value?.destination
    return currentDestination?.hierarchy?.any { destination ->
        routes.any { route -> destination.hasRoute(route::class) }
    } == true
}

@Composable
fun NavController.shouldShowBottomBar(): Boolean {
    return isOnAnyRoute(Routes.Search, Routes.ProgressTracker, Routes.Library)
}

@Composable
fun NavController.isBottomNavItemSelected(route: Routes): Boolean {
    return isOnAnyRoute(route)
}
