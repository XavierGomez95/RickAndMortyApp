package com.rickandmortyapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rickandmortyapi.navigation.AppNavigator
import com.rickandmortyapi.navigation.MainScreen
import com.rickandmortyapi.ui.composables.main_screens.CharacterScreen
import com.rickandmortyapi.ui.composables.main_screens.EpisodeScreen

@Composable
fun MainNavigationWrapper (
    mainNavHost: NavHostController,
    modifier: Modifier,
    navigator: AppNavigator
) {
    NavHost(
        navController = mainNavHost,
        startDestination = MainScreen.CharacterScreen,
        modifier = modifier
    ) {
        composable<MainScreen.CharacterScreen> {
            CharacterScreen(
                modifier = modifier,
                navigator = navigator
            )
        }
        composable<MainScreen.EpisodeScreen> {
            EpisodeScreen(
                modifier = modifier,
                navigator = navigator,
            )
        }
    }
}