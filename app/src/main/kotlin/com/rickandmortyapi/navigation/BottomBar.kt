package com.rickandmortyapi.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rickandmortyapi.ui.theme.LocalSemanticColors
import com.rickandmortyapi.ui.theme.colors.customNavigationBarItemColors

@Composable
fun BottomBar(
    navigator: AppNavigator,
    currentDestinationRoute: String?,
    screens: List<MainScreen>
) {
    val colors = LocalSemanticColors.current

    NavigationBar(
        tonalElevation = 4.dp,
        containerColor = colors.bottomBarBackground,
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
                        contentDescription = null,
                        tint = if (selected)
                            colors.bottomBarSelectedIcon
                        else
                            colors.bottomBarUnselectedIcon
                    )
                },
                colors = customNavigationBarItemColors(),
                label = {
                    Text(
                        stringResource(id = screen.name),
                        color = if (selected)
                            colors.bottomBarSelectedIcon
                        else
                            colors.bottomBarUnselectedIcon
                    )
                }
            )
        }
    }
}