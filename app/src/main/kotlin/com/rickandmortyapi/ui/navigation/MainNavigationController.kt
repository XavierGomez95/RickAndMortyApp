package com.rickandmortyapi.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rickandmortyapi.navigation.AppNavigator
import com.rickandmortyapi.navigation.BottomBar
import com.rickandmortyapi.navigation.MainScreen
import com.rickandmortyapi.navigation.TopBar
import kotlinx.serialization.Serializable


@Serializable
object MainNavigation

@Composable
fun MainNavigationController(
    navigator: AppNavigator,
) {
    val mainNavHost = rememberNavController()

    LaunchedEffect(Unit) {
        navigator.innerNavController = mainNavHost
    }

    val backStackEntry by mainNavHost.currentBackStackEntryAsState()
    val currentDestinationRoute = backStackEntry?.destination?.route

    val screens = listOf(MainScreen.CharacterScreen, MainScreen.EpisodeScreen)


    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(
                navigator = navigator,
                currentDestinationRoute = currentDestinationRoute,
                screens = screens
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            MainNavigationWrapper(
                mainNavHost = mainNavHost,
                modifier = Modifier,
                navigator = navigator
            )
        }
    }
}