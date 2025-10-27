package com.rickandmortyapi.navigation

import androidx.navigation.NavHostController
import com.rickandmortyapi.ui.composables.character.CharacterDetailNav
import com.rickandmortyapi.ui.composables.episode.EpisodeDetailNav
import com.rickandmortyapi.ui.navigation.MainNavigation
import com.rickandmortyapi.ui.Splash

class AppNavigator(
    private val rootNavController: NavHostController
) {
    var innerNavController: NavHostController? = null

    fun navigateToMainScreen(screen: MainScreen) {
        innerNavController?.navigate(screen) {
            popUpTo(MainScreen.CharacterScreen) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateFromSplashToMain() {
        rootNavController.navigate(MainNavigation) {
            popUpTo(Splash) { inclusive = true }
        }
    }

    fun navigateToCharacterDetail(id: Int) {
        rootNavController.navigate(CharacterDetailNav(id))
    }

    fun navigateToEpisodeDetail(id: Int) {
        rootNavController.navigate(EpisodeDetailNav(id))
    }
}