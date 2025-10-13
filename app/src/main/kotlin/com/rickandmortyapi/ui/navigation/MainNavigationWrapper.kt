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
import com.rickandmortyapi.ui.composables.episode.EpisodeDetailNav
import com.rickandmortyapi.ui.composables.episode.EpisodeDetailScreen
import com.rickandmortyapi.ui.composables.main_screens.CharacterScreen
import com.rickandmortyapi.ui.composables.main_screens.EpisodeScreen

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

        composable<EpisodeDetailNav> { backStackEntry ->
            val episodeDetail: EpisodeDetailNav = backStackEntry.toRoute()
            EpisodeDetailScreen(episodeId = episodeDetail.id)
        }

        composable<CharacterDetailNav> { backStackEntry ->
            val characterDetail: CharacterDetailNav = backStackEntry.toRoute()
            CharacterDetailScreen(characterId = characterDetail.id)
        }
    }
}