package com.bcoding.readtracker.core.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bcoding.readtracker.core.navigation.Routes.*

fun NavGraphBuilder.homeNavGraph(
    modifier: Modifier,
    showDetails: () -> Unit
) {
    navigation<HomeGraph>(
        startDestination = Home
    ) {
        composable<Home> {
            Column(
                modifier = modifier.padding()
            ) {
                Text(text = "home")
                Button(onClick = { showDetails() }) { 
                    Text("go to details") 
                }
            }
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
