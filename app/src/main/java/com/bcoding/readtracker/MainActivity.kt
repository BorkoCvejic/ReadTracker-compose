package com.bcoding.readtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bcoding.readtracker.core.navigation.BottomNavigationBar
import com.bcoding.readtracker.core.navigation.MainNavHost
import com.bcoding.readtracker.core.navigation.shouldShowBottomBar
import com.bcoding.readtracker.core.presentation.theme.ReadTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            ReadTrackerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (navController.shouldShowBottomBar()) {
                            BottomNavigationBar(
                                navController = navController
                            )
                        }
                    }
                ) { innerPadding ->
                    MainNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }
}
