package com.rickandmortyapi.ui.colors

import androidx.compose.ui.graphics.Color
import org.junit.Assert.*
import org.junit.Test

class CharacterStatusTest {
    private val expectedGreen = Color(0xFF269246)
    private val expectedRed = Color(0xFFC14C64)
    private val expectedGrey = Color(0xFF7A7A7A)

    @Test
    fun `ALIVE color should match expected hex value`() {
        assertEquals(expectedGreen, CharacterStatus.ALIVE.color)
    }

    @Test
    fun `DEAD color should match expected hex value`() {
        assertEquals(expectedRed, CharacterStatus.DEAD.color)
    }

    @Test
    fun `UNKNOWN color should match expected hex value`() {
        assertEquals(expectedGrey, CharacterStatus.UNKNOWN.color)
    }

    @Test
    fun `getColor should return correct color for Alive status`() {
        val result = CharacterStatus.getColor("Alive")
        assertEquals(CharacterStatus.ALIVE.color, result)
        assertEquals(expectedGreen, result)
    }

    @Test
    fun `getColor should return correct color for Dead status`() {
        val result = CharacterStatus.getColor("Dead")
        assertEquals(expectedRed, result)
    }

    @Test
    fun `getColor should return UNKNOWN color for null input`() {
        val result = CharacterStatus.getColor(null)
        assertEquals(CharacterStatus.UNKNOWN.color, result)
        assertEquals(expectedGrey, result)
    }

    @Test
    fun `getColor should return UNKNOWN color for empty or invalid input`() {
        val resultEmpty = CharacterStatus.getColor("")
        val resultInvalid = CharacterStatus.getColor("Something else")

        assertEquals(CharacterStatus.UNKNOWN.color, resultEmpty)
        assertEquals(CharacterStatus.UNKNOWN.color, resultInvalid)
        assertEquals(expectedGrey, resultEmpty)
        assertEquals(expectedGrey, resultInvalid)
    }
}