package com.rickandmortyapi.utils

import com.rickandmortyapi.data.database.entities.EpisodeEntity
import com.rickandmortyapi.data.model.EpisodeModel
import org.junit.Assert.*
import org.junit.Test

class EpisodeUtilsTest {
    // https://docs.junit.org/current/user-guide/#writing-tests-assertions-kotlin

    @Test
    fun `episodeEntityToModel should correctly map fields`() {
        val entity = EpisodeEntity(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01",
            url = "https://rickandmortyapi.com/api/episode/1",
            created = "2017-11-10T12:56:33.798Z"
        )

        val model = episodeEntityToModel(entity)

        assertEquals(model.id, entity.id)
        assertEquals(model.name, entity.name)
        assertEquals(model.airDate, entity.airDate)
        assertEquals(model.episode, entity.episode)
        assertEquals(model.url, "")
        assertEquals(model.created, "")
    }

    @Test
    fun `episodeModelToEntity should correctly map fields`() {
        val model = EpisodeModel(
            id = 2,
            name = "Lawnmower Dog",
            airDate = "December 9, 2013",
            episode = "S01E02",
            url = "https://rickandmortyapi.com/api/episode/2",
            created = "2017-11-10T12:56:33.798Z"
        )

        val entity = episodeModelToEntity(model)

        assertEquals(model.id, entity.id)
        assertEquals(model.name, entity.name)
        assertEquals(model.airDate, entity.airDate)
        assertEquals(model.episode, entity.episode)
        assertEquals(model.url, entity.url)
        assertEquals(model.created, entity.created)

        assertEquals(2, entity.id)
        assertEquals("Lawnmower Dog", entity.name)
        assertEquals("December 9, 2013", entity.airDate)
        assertEquals("S01E02", entity.episode)
        assertEquals("https://rickandmortyapi.com/api/episode/2", entity.url)
        assertEquals("2017-11-10T12:56:33.798Z", entity.created)
    }

    @Test
    fun `entity to model and back should preserve data integrity`() {
        val originalEntity = EpisodeEntity(
            id = 3,
            name = "Anatomy Park",
            airDate = "December 16, 2013",
            episode = "S01E03",
            url = "https://rickandmortyapi.com/api/episode/3",
            created = "2017-11-10T12:56:33.798Z"
        )

        val convertedModel = episodeEntityToModel(originalEntity)
        val convertedEntity = episodeModelToEntity(convertedModel)

        assertEquals(originalEntity.id, convertedEntity.id)
        assertEquals(originalEntity.name, convertedEntity.name)
        assertEquals(originalEntity.airDate, convertedEntity.airDate)
        assertEquals(originalEntity.episode, convertedEntity.episode)

        assertEquals(3, convertedEntity.id)
        assertEquals("Anatomy Park", convertedEntity.name)
        assertEquals("December 16, 2013", convertedEntity.airDate)
        assertEquals("S01E03", convertedEntity.episode)
    }

    @Test
    fun `convertAirDateForUi should return date before T`() {
        val result = convertAirDateForUi("2023-10-01T12:00:00Z")
        assertEquals("2023-10-01", result)
    }

    @Test
    fun `convertAirDateForUi should return Unknown if string is invalid`() {
        val result = convertAirDateForUi("invalid_date")
        assertEquals("invalid_date", result) // There’s no 'T', so full string stays the same
    }

    @Test
    fun `convertAirDateForUi should return Unknown if empty`() {
        val result = convertAirDateForUi("")
        assertEquals("Unknown", result)
    }

    @Test
    fun `convertSeasonAndEpisodeForUi should extract season and episode correctly`() {
        val result = convertSeasonAndEpisodeForUi("S03E07")
        assertEquals("03", result["season"])
        assertEquals("07", result["episode"])
    }

    @Test
    fun `convertSeasonAndEpisodeForUi should handle malformed input gracefully`() {
        val result = convertSeasonAndEpisodeForUi("E")
        assertEquals("?", result["season"])
        assertEquals("?", result["episode"])
    }

    @Test
    fun `convertSeasonAndEpisodeForUi should handle empty string`() {
        val result = convertSeasonAndEpisodeForUi("")
        assertEquals("?", result["season"])
        assertEquals("?", result["episode"])
    }
}