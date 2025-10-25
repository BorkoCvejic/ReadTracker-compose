package com.bcoding.readtracker.core.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bcoding.readtracker.book.presentation.home.HomeScreenRoot
import com.bcoding.readtracker.book.presentation.home.HomeViewModel
import com.bcoding.readtracker.core.presentation.navigation.Routes.*
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.homeNavGraph(
    modifier: Modifier,
    showDetails: () -> Unit
) {
    navigation<HomeGraph>(
        startDestination = Home
    ) {
        composable<Home> {
            val homeViewModel = koinViewModel<HomeViewModel>()
            HomeScreenRoot(
                modifier = modifier,
                homeViewModel = homeViewModel,
                showDetails = { showDetails() }
            )
        }
    }
}

fun NavGraphBuilder.progressTrackerGraph(modifier: Modifier) {
    navigation<ProgressTrackerGraph>(
        startDestination = ProgressTracker
    ) {
        composable<ProgressTracker> {
            Text(modifier = modifier, text = "progress tracker")
        }
    }
}

fun NavGraphBuilder.favoritesGraph(modifier: Modifier) {
    navigation<FavoritesGraph>(
        startDestination = Favorites
    ) {
        composable<Favorites> {
            Text(modifier = modifier, text = "Favorites")
        }
    }
}

fun NavGraphBuilder.sharedGraph(modifier: Modifier) {
    composable<BookDetails> {
        Text(modifier = modifier, text = "book details")
    }
}