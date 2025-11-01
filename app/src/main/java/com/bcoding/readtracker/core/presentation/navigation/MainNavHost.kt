package com.bcoding.readtracker.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bcoding.readtracker.core.presentation.navigation.Routes.*

@Composable
fun MainNavHost(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SearchGraph
    ) {
        searchNavGraph(
            modifier = modifier,
            showDetails = { bookId -> navController.navigate(BookDetails(bookId)) }
        )
        progressTrackerGraph(modifier = modifier)
        favoritesGraph(modifier = modifier)
        sharedGraph(modifier = modifier)
    }
}