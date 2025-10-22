package com.bcoding.readtracker.core.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.bcoding.readtracker.core.navigation.BottomNavItem.*
import com.bcoding.readtracker.core.presentation.theme.dimensions

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val bottomNavigationItems =
        listOf(
            HomeNavItem,
            ProgressTrackerNavItem,
            FavoritesNavItem,
        )

    NavigationBar {
        bottomNavigationItems.forEach { item ->
            val isSelected = navController.isBottomNavItemSelected(item.route)

            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(MaterialTheme.dimensions.bottomNavIcon),
                        painter = painterResource(id = item.icon),
                        contentDescription = item.name,
                        tint = if (isSelected) {
                            LocalContentColor.current
                        } else {
                            LocalContentColor.current.copy(alpha = MaterialTheme.dimensions.bottomNavIconAlpha)
                        }
                    )
                },
                label = { Text(item.name) },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}