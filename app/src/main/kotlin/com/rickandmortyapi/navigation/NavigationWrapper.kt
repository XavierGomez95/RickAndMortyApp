package com.rickandmortyapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rickandmortyapi.ui.Splash
import com.rickandmortyapi.ui.SplashScreen
import com.rickandmortyapi.ui.navigation.MainNavigationController
import com.rickandmortyapi.ui.navigation.MainNavigation

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen(
                navController
            )
        }
        composable<MainNavigation> {
            MainNavigationController()
        }
    }
}