package com.rickandmortyapi.ui.theme

import androidx.compose.ui.graphics.Color

data class SemanticColors (
    // Failure Text
    val error: Color,

    // UI ELEMENTS
    // TopBar
    val topBarBackground: Color,

    // Card
    val cardBackground: Color,
    val cardTittle: Color,

    // Character Details
    val characterName: Color,
    val characterGender: Color,
    val characterSpecie: Color,

    // Character Details
    val episodeName: Color,
    val episodeDate: Color,
    val episodeSeason: Color,
    val episode: Color,

    // BottomBar
    val bottomBarBackground: Color,
    val bottomBarSelectedIcon: Color,
    val bottomBarUnselectedIcon: Color,
    val bottomBarSelectedText: Color,
    val bottomBarUnselectedText: Color,
    val bottomBarIndicator: Color,

    // Background
    val customBackground: Color,

    // SearchBar
    val placeholder: Color,
    val cursorColor: Color,
    val focusedLeadingIconColor: Color,
    val unfocusedLeadingIconColor: Color,
    val focusedTrailingIconColor: Color,
    val unfocusedTrailingIconColor: Color,
    val focusedTextColor: Color,
    val searchBarBorder: Color,
)
