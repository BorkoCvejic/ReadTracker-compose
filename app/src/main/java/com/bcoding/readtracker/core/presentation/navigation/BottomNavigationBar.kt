package com.bcoding.readtracker.core.presentation.navigation

import android.content.res.Configuration
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.bcoding.readtracker.core.presentation.navigation.BottomNavItem.*
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme
import com.bcoding.readtracker.core.presentation.theme.appDimensions

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val bottomNavigationItems =
        listOf(
            SearchNavItem,
            LibraryNavItem,
        )

    NavigationBar {
        bottomNavigationItems.forEach { bottomNavItem ->
            val isSelected = navController.isBottomNavItemSelected(bottomNavItem.route)

            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(MaterialTheme.appDimensions.bottomNavIcon),
                        painter = painterResource(id = bottomNavItem.icon),
                        contentDescription = stringResource(bottomNavItem.name),
                        tint = if (isSelected) {
                            LocalContentColor.current
                        } else {
                            LocalContentColor.current.copy(alpha = MaterialTheme.appDimensions.bottomNavIconAlpha)
                        }
                    )
                },
                label = {
                    Text(
                        text = stringResource(bottomNavItem.name),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(bottomNavItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Preview(
    name = "Light mode",
    showBackground = true,
    backgroundColor = 0xFFFFFBFE
)
@Preview(
    name = "Dark mode",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BottomNavigationBarPreview() {
    ReadTrackerTheme {
        BottomNavigationBar(
            navController = rememberNavController()
        )
    }
}
