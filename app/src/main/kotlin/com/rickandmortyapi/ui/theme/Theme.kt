package com.rickandmortyapi.ui.theme


import androidx.compose.material3.Typography

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


private val LightSemanticColors = SemanticColors(
    // Failure Text
    error                       = Color(0xFFCF0000),

    // UI ELEMENTS
    // TopBar
    topBarBackground            = Color(0xFFFFFFFF),

    // Card
    cardBackground              = Color(0xFFE3ECE4),
    cardTittle                  = Color(0xFF193334),

    // Character Details
    characterName               = Color(0xFF193334),
    characterGender               = Color(0xFF193334),
    characterSpecie               = Color(0xFF193334),

    // Episode Details
    episodeName                 = Color(0xFF193334),
    episodeDate                 = Color(0xFF193334),
    episodeSeason               = Color(0xFF193334),
    episode                     = Color(0xFF193334),

    // BottomBar
    bottomBarBackground         = Color(0xFFFFFFFF),
    bottomBarSelectedIcon       = Color(0xFF193334),//Color(0xFF0A8643),
    bottomBarUnselectedIcon     = Color(0xFF3a9f39),//Color(0xFF13b1c9),
    bottomBarSelectedText       = Color(0xFF193334),
    bottomBarUnselectedText     = Color(0xFF3a9f39),
    bottomBarIndicator          = Color(0xFF193334),

    // Background
    customBackground            = Color(0xFFFFFFFF),

    // SearchBar
    placeholder                 = Color(0xFF193334),
    cursorColor                 = Color(0xFF193334),
    focusedLeadingIconColor     = Color(0xFF193334),
    unfocusedLeadingIconColor   = Color(0xFF193334),
    focusedTrailingIconColor    = Color(0xFF193334),
    unfocusedTrailingIconColor  = Color(0xFF193334),
    focusedTextColor            = Color(0xFF193334),
    searchBarBorder             = Color(0xFF9CC290)
)

private val DarkSemanticColors = SemanticColors(
    // Failure Text
    error                       = Color(0xFFCF6679),

    // UI Elements
    // TopBar
    topBarBackground            = Color(0xFF193334),

    // Card
    cardBackground              = Color(0xFFfefac3),
    cardTittle                  = Color(0xFF193334),

    // Character Details
    characterName               = Color(0xFFFFFFFF),
    characterGender             = Color(0xFFFFFFFF),
    characterSpecie             = Color(0xFFFFFFFF),

    // Episode Details
    episodeName                 = Color(0xFFFFFFFF),
    episodeDate                 = Color(0xFFFFFFFF),
    episodeSeason               = Color(0xFFFFFFFF),
    episode                     = Color(0xFFFFFFFF),

    // BottomBar
    bottomBarBackground         = Color(0xFF193334),
    bottomBarSelectedIcon       = Color(0xFFFFFFFF),
    bottomBarUnselectedIcon     = Color(0xFFBCBCBC),
    bottomBarSelectedText       = Color(0xFFFFFFFF),
    bottomBarUnselectedText     = Color(0xFFBCBCBC),
    bottomBarIndicator          = Color(0xFFFFFFFF),

    // Background
    customBackground            = Color(0xFF193334),

    // SearchBar
    placeholder                 = Color(0xFFFFFFFF),
    cursorColor                 = Color(0xFFFFFFFF),
    focusedLeadingIconColor     = Color(0xFFFFFFFF),
    unfocusedLeadingIconColor   = Color(0xFFFFFFFF),
    focusedTrailingIconColor    = Color(0xFFFFFFFF),
    unfocusedTrailingIconColor  = Color(0xFFFFFFFF),
    focusedTextColor            = Color(0xFFFFFFFF),
    searchBarBorder             = Color(0xFFd2e054) // Color(0xFF9CC290)
)

val LocalSemanticColors = staticCompositionLocalOf { LightSemanticColors }

@Composable
fun RickAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Android 12+ or newer version
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkSemanticColors else LightSemanticColors

    val typographyInstance = Typography(
        bodyLarge = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        ),
        titleLarge = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        ),
    )

    CompositionLocalProvider(LocalSemanticColors provides colors) {
        MaterialTheme(
            typography = typographyInstance,
            content = content
        )
    }
}
