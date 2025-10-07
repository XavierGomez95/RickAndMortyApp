package com.rickandmortyapi.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Public
import androidx.compose.ui.graphics.vector.ImageVector
import com.rickandmortyapi.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class MainScreen (
    @StringRes val name: Int,
    @Transient val icon: ImageVector? = Icons.Filled.Group
) {
    @Serializable
    object CharacterScreen : MainScreen(R.string.bottom_bar__characters, Icons.Filled.Group)
    @Serializable
    object EpisodeScreen : MainScreen(R.string.bottom_bar__episodes, Icons.Filled.Movie)
    @Serializable
    object LocationScreen : MainScreen(R.string.bottom_bar__locations, Icons.Filled.Public)
}