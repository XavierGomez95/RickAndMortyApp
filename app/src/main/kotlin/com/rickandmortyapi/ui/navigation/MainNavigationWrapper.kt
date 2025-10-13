package com.rickandmortyapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rickandmortyapi.navigation.MainScreen
import com.rickandmortyapi.ui.composables.character.CharacterDetailNav
import com.rickandmortyapi.ui.composables.character.CharacterDetailScreen
import com.rickandmortyapi.ui.composables.main_screens.CharacterScreen
import com.rickandmortyapi.ui.composables.main_screens.EpisodeScreen
import com.rickandmortyapi.ui.composables.main_screens.LocationScreen

@Composable
fun MainNavigationWrapper (
    mainNavHost: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = mainNavHost,
        startDestination = MainScreen.CharacterScreen,
        modifier = modifier
    ) {
        composable<MainScreen.CharacterScreen> {
            CharacterScreen(
                navController = mainNavHost,
                modifier = modifier
            )
        }
        composable<MainScreen.EpisodeScreen> {
            EpisodeScreen(
                navController = mainNavHost,
                modifier = modifier
            )
        }
        composable<MainScreen.LocationScreen> {
            LocationScreen()
        }

        composable<CharacterDetailNav> { backStackEntry ->
            val characterDetail: CharacterDetailNav = backStackEntry.toRoute()
            CharacterDetailScreen(characterId = characterDetail.id)
        }
    }
}