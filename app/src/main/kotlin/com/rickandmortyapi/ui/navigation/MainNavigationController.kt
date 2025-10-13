package com.rickandmortyapi.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.rickandmortyapi.navigation.BottomBar
import com.rickandmortyapi.navigation.MainScreen
import com.rickandmortyapi.navigation.TopBar
import kotlinx.serialization.Serializable


@Serializable
object MainNavigation

@Composable
fun MainNavigationController() {
    val screens = listOf(MainScreen.CharacterScreen, MainScreen.EpisodeScreen)

    val mainNavHost = rememberNavController()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(
                navController = mainNavHost,
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
                modifier = Modifier
            )
        }
    }
}