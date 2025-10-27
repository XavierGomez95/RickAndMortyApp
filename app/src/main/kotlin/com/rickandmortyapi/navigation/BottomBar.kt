package com.rickandmortyapi.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    navigator: AppNavigator,
    currentDestinationRoute: String?,
    screens: List<MainScreen>
) {
    NavigationBar (
        tonalElevation = 4.dp,
    ) {
        screens.forEach { screen ->
            val selected = currentDestinationRoute == screen::class.qualifiedName

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navigator.navigateToMainScreen(screen)
                    }
                },
                icon = {
                    Icon(
                        imageVector = screen.icon!!,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        stringResource(id = screen.name)
                    )
                }
            )
        }
    }
}