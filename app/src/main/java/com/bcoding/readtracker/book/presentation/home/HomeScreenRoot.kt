package com.bcoding.readtracker.book.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreenRoot(
    modifier: Modifier,
    homeViewModel: HomeViewModel,
    showDetails: () -> Unit
) {
    HomeScreen(modifier, showDetails = { showDetails() })
}

@Composable
fun HomeScreen(
    modifier: Modifier,
    showDetails: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "home")
        Button(onClick = { showDetails() }) {
            Text("go to details")
        }
    }
}