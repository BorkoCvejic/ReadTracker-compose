package com.bcoding.readtracker.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bcoding.readtracker.book.presentation.SelectedBookViewModel
import com.bcoding.readtracker.core.presentation.navigation.Routes.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavHost(
    modifier: Modifier,
    navController: NavHostController
) {
    // needed because BookDetailsScreen can be opened from different graphs which provide Book model
    val selectedBookViewModel = koinViewModel<SelectedBookViewModel>()

    NavHost(
        navController = navController,
        startDestination = SearchGraph
    ) {
        searchNavGraph(
            modifier = modifier,
            showDetails = { book ->
                selectedBookViewModel.onSelectBook(book)
                navController.navigate(BookDetails)
            }
        )
        progressTrackerGraph(modifier = modifier)
        favoritesGraph(modifier = modifier)
        sharedGraph(
            selectedBookViewModel = selectedBookViewModel,
            navigateUp = {
                navController.navigateUp()
            }
        )
    }
}
