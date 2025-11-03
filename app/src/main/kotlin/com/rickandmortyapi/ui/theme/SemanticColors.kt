package com.rickandmortyapi.ui.theme

import androidx.compose.ui.graphics.Color

data class SemanticColors (
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val surface: Color,
    val onBackground: Color,
    val onSurface: Color,
    val error: Color,

    val text: Color,

    // UI ELEMENTS
    // TopBar
    val topBarBackground: Color,
    val topBarContent: Color,

    // Card
    val cardBackground: Color,
    val cardContent: Color,
    val cardTittle: Color,

    // BottomBar
    val bottomBarBackground: Color,
    val bottomBarContent: Color,
    val bottomBarSelectedIcon: Color,
    val bottomBarUnselectedIcon: Color,
    val bottomBarIndicator: Color,
    val bottomBarDefaultIconColor: Color,
    val bottomBarDefaultTextColor: Color,


    // SearchBar
    val searchBarBorder: Color,
)
