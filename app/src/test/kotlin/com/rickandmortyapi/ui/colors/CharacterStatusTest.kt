package com.rickandmortyapi.ui.colors

import androidx.compose.ui.graphics.Color
import org.junit.Assert.*
import org.junit.Test

class CharacterStatusTest {
    private val expectedGreen = Color(0xFF269246)
    private val expectedRed = Color(0xFFC14C64)
    private val expectedGrey = Color(0xFF7A7A7A)

    // ------------------------------------------------------------------------
    // TEST 1: Verifies that the ALIVE color in CharacterStatus matches
    // the expected green hex value (#269246).
    // ------------------------------------------------------------------------
    @Test
    fun `ALIVE color should match expected hex value`() {
        assertEquals(expectedGreen, CharacterStatus.ALIVE.color)
    }

    // ------------------------------------------------------------------------
    // TEST 2: Ensures that the DEAD color in CharacterStatus matches
    // the expected red hex value (#C14C64).
    // ------------------------------------------------------------------------
    @Test
    fun `DEAD color should match expected hex value`() {
        assertEquals(expectedRed, CharacterStatus.DEAD.color)
    }

    // ------------------------------------------------------------------------
    // TEST 3: Confirms that the UNKNOWN color in CharacterStatus matches
    // the expected grey hex value (#7A7A7A).
    // ------------------------------------------------------------------------
    @Test
    fun `UNKNOWN color should match expected hex value`() {
        assertEquals(expectedGrey, CharacterStatus.UNKNOWN.color)
    }

    // ------------------------------------------------------------------------
    // TEST 4: Validates that getColor("Alive") returns the correct ALIVE color.
    // Ensures both the enum color and expected green hex value match.
    // ------------------------------------------------------------------------
    @Test
    fun `getColor should return correct color for Alive status`() {
        val result = CharacterStatus.getColor("Alive")
        assertEquals(CharacterStatus.ALIVE.color, result)
        assertEquals(expectedGreen, result)
    }

    // ------------------------------------------------------------------------
    // TEST 5: Validates that getColor("Dead") returns the correct DEAD color.
    // Ensures both the enum color and expected red hex value match.
    // ------------------------------------------------------------------------
    @Test
    fun `getColor should return correct color for Dead status`() {
        val result = CharacterStatus.getColor("Dead")
        assertEquals(expectedRed, result)
    }

    // ------------------------------------------------------------------------
    // TEST 6: Ensures that getColor(null) returns the UNKNOWN color.
    // Ensures both the enum color and expected grey hex value match.
    // ------------------------------------------------------------------------
    @Test
    fun `getColor should return UNKNOWN color for null input`() {
        val result = CharacterStatus.getColor(null)
        assertEquals(CharacterStatus.UNKNOWN.color, result)
        assertEquals(expectedGrey, result)
    }

    // ------------------------------------------------------------------------
    // TEST 7: Validates that getColor("") and getColor("Something else")
    // return the UNKNOWN color when input is empty or invalid.
    // Ensures both the enum color and expected grey hex value.
    // ------------------------------------------------------------------------

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