package com.rickandmortyapi.data.utils

import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.model.EpisodeModel
import org.junit.Assert.*
import org.junit.Test

class ResourceTest {
    // https://docs.junit.org/current/user-guide/#writing-tests-assertions-kotlin

    // ------------------------------------------------------------------------
    // TEST 1: Verifies that a Resource.Success object created with a String
    // correctly stores and exposes its data value.
    // ------------------------------------------------------------------------
    @Test
    fun `Success should contain correct data with String`() {
        val success1 = Resource.Success(data = "Rick Sanchez")
        assertEquals("Rick Sanchez", success1.data)
    }

    // ------------------------------------------------------------------------
    // TEST 2: Ensures that Resource.Success correctly holds and returns
    // a list of CharacterModel objects with accurate field values.
    // ------------------------------------------------------------------------
    @Test
    fun `Success should contain correct data with list of CharacterModel`() {
        val characters = listOf(
            CharacterModel(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                gender = "Male",
                image = "rick.png"
            ),
            CharacterModel(
                id = 2,
                name = "Morty Smith",
                status = "Alive",
                species = "Human",
                gender = "Male",
                image = "morty.png"
            )
        )

        val resource = Resource.Success(data = characters)

        assertEquals("Rick Sanchez", resource.data[0].name)
        assertEquals("Morty Smith", resource.data[1].name)
        for (character in resource.data) {
            assertEquals("Human", character.species)
        }
        assertEquals(2, resource.data.size)

    }

    // ------------------------------------------------------------------------
    // TEST 3: Validates that Resource.Success correctly stores and exposes
    // a list of EpisodeModel objects with accurate name, airDate, and episode.
    // ------------------------------------------------------------------------
    @Test
    fun `Success should contain correct data with list of EpisodeModel`() {
        val episodes = listOf(
            EpisodeModel(
                id = 1,
                name = "Pilot",
                airDate = "December 2, 2013",
                episode = "S01E01"
            ),
            EpisodeModel(
                id = 2,
                name = "Lawnmower Dog",
                airDate = "December 9, 2013",
                episode = "S01E02"
            )
        )

        val resource = Resource.Success(data = episodes)

        assertEquals("Pilot", resource.data[0].name)
        assertEquals("S01E01", resource.data[0].episode)
        assertTrue(resource.data[0].airDate.contains("December 2, 2013"))

        assertEquals("Lawnmower Dog", resource.data[1].name)
        assertEquals("S01E02", resource.data[1].episode)
        assertTrue(resource.data[1].airDate.contains("December 9, 2013"))
        assertEquals(2, resource.data.size)
    }

    // ------------------------------------------------------------------------
    // TEST 4: Ensures that Resource.Failure is implemented as a singleton.
    // Multiple references should point to the same instance.
    // ------------------------------------------------------------------------
    @Test
    fun `Failure should be singleton`() {
        val firstSingleton = Resource.Failure
        val secondSingleton = Resource.Failure
        assertSame(firstSingleton, secondSingleton) // Same instance
    }

    // ------------------------------------------------------------------------
    // TEST 5: Ensures that Resource.Loading is implemented as a singleton.
    // Multiple references should return the exact same instance.
    // ------------------------------------------------------------------------
    @Test
    fun `Loading should be singleton`() {
        val firstSingleton = Resource.Loading
        val secondSingleton = Resource.Loading
        assertSame(firstSingleton, secondSingleton)
    }

    // ------------------------------------------------------------------------
    // TEST 6: Ensures that Resource.Init is implemented as a singleton.
    // Multiple references should return the same object instance.
    // ------------------------------------------------------------------------
    @Test
    fun `Init should be singleton`() {
        val firstSingleton = Resource.Init
        val secondSingleton = Resource.Init
        assertSame(firstSingleton, secondSingleton)
    }
}