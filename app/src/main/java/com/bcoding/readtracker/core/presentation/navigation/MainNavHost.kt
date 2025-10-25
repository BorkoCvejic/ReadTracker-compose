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
        startDestination = HomeGraph
    ) {
        homeNavGraph(modifier, showDetails = { navController.navigate(BookDetails) })
        progressTrackerGraph(modifier)
        favoritesGraph(modifier)
        sharedGraph(modifier)
    }
}