package com.bcoding.readtracker.core.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bcoding.readtracker.core.navigation.Routes.*

@Composable
fun MainNavHost(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeGraph
    ) {
        homeNavGraph(modifier, showDetails = { navController.navigate(BookDetails) })
        progressTrackerGraph(modifier)
        favoritesGraph(modifier)

        composable<BookDetails> {
            Text(modifier = modifier, text = "book details")
        }
    }
}