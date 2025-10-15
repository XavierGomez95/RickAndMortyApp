package com.rickandmortyapi.ui.colors

import androidx.compose.ui.graphics.Color

enum class CharacterStatus(val color: Color) {
    ALIVE(Color(0xFF269246)),
    DEAD(Color(0xFFC14C64)),
    UNKNOWN(Color(0xFF7A7A7A));

    // https://rickandmortyapi.com/documentation/#character-schema
    companion object{ // static equivalent in Java
        fun getColor(status: String?): Color {
            return when (status) {
                "Alive" -> ALIVE.color
                "Dead" -> DEAD.color
                else -> UNKNOWN.color
            }
        }
    }
}

