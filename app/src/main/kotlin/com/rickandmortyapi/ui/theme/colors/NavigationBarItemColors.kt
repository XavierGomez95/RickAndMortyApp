package com.rickandmortyapi.ui.theme.colors

import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import com.rickandmortyapi.ui.theme.LocalSemanticColors

@Composable
fun customNavigationBarItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = LocalSemanticColors.current.bottomBarSelectedIcon,
    unselectedIconColor = LocalSemanticColors.current.bottomBarUnselectedIcon,
    selectedTextColor = LocalSemanticColors.current.bottomBarSelectedText,
    unselectedTextColor = LocalSemanticColors.current.bottomBarUnselectedText,
    indicatorColor = LocalSemanticColors.current.bottomBarIndicator.copy(alpha = 0.12f)
)