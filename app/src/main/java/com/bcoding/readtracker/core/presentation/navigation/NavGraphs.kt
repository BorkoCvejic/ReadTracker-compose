package com.bcoding.readtracker.core.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.bcoding.readtracker.book.presentation.search.SearchScreenRoot
import com.bcoding.readtracker.book.presentation.search.SearchViewModel
import com.bcoding.readtracker.core.presentation.navigation.Routes.*
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.searchNavGraph(
    modifier: Modifier,
    showDetails: (String) -> Unit
) {
    navigation<SearchGraph>(
        startDestination = Search
    ) {
        composable<Search> {
            val searchViewModel = koinViewModel<SearchViewModel>()
            SearchScreenRoot(
                modifier = modifier,
                searchViewModel = searchViewModel,
                showDetails = { bookId -> showDetails(bookId) }
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
    composable<BookDetails> { navStackEntry ->
        val bookId = navStackEntry.toRoute<BookDetails>().bookId
        Text(modifier = modifier, text = bookId)
    }
}