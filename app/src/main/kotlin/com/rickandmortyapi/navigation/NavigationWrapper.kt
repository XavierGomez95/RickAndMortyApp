package com.rickandmortyapi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rickandmortyapi.ui.Splash
import com.rickandmortyapi.ui.SplashScreen
import com.rickandmortyapi.ui.composables.character.CharacterDetailNav
import com.rickandmortyapi.ui.composables.character.CharacterDetailScreen
import com.rickandmortyapi.ui.composables.episode.EpisodeDetailNav
import com.rickandmortyapi.ui.composables.episode.EpisodeDetailScreen
import com.rickandmortyapi.ui.navigation.MainNavigationController
import com.rickandmortyapi.ui.navigation.MainNavigation

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val navigator = remember { AppNavigator(navController) }

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen(
                navigator
            )
        }
        composable<MainNavigation> {
            MainNavigationController(
                navigator = navigator,
            )
        }

        composable<EpisodeDetailNav> { backStackEntry ->
            val episodeDetail: EpisodeDetailNav = backStackEntry.toRoute()
            EpisodeDetailScreen(
                episodeId = episodeDetail.id,
            )
        }

        composable<CharacterDetailNav> { backStackEntry ->
            val characterDetail: CharacterDetailNav = backStackEntry.toRoute()
            CharacterDetailScreen(
                characterId = characterDetail.id,
            )
        }
    }
}