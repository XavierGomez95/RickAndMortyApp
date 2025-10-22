package com.rickandmortyapi.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rickandmortyapi.data.database.AppDatabase
import com.rickandmortyapi.data.database.entities.EpisodeEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class EpisodeDaoTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var episodeDao: EpisodeDao

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        episodeDao = appDatabase.episodeDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    // ------------------------------------------------------------------------
    // TEST 1: Inserts an episode into the database and checks that getEpisodes() returns the
    // correct record with the expected values.
    // ------------------------------------------------------------------------
    @Test
    fun insertAndRetrieveEpisodes() = runBlocking {
        val episode = EpisodeEntity(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01",
            url = null,
            created = null
        )

        episodeDao.insertEpisode(episode)
        val result = episodeDao.getEpisodes()

        assertEquals(1, result.size)
        assertEquals("Pilot", result.first().name)
        assertEquals("S01E01", result.first().episode)
    }

    // ------------------------------------------------------------------------
    // TEST 2: Inserts an episode and checks that getEpisodeById(id) returns the correct record with the
    // expected values.
    // ------------------------------------------------------------------------
    @Test
    fun insertAndRetrieveSingleEpisode() = runBlocking {
        val episode = EpisodeEntity(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01",
            url = null,
            created = null
        )

        episodeDao.insertEpisode(episode)
        val result = episodeDao.getEpisodeById(1)

        assertEquals("Pilot", result?.name)
        assertEquals("December 2, 2013", result?.airDate)
        assertEquals("S01E01", result?.episode)
        assertEquals(null, result?.url)
        assertEquals(null, result?.created)
    }

    // ------------------------------------------------------------------------
    // TEST 3: Inserts a list of episodes and ensures all data retrieved is correct.
    // ------------------------------------------------------------------------
    @Test
    fun insertMultipleEpisodes_andRetrieveAll() = runBlocking {
        val episodes = listOf(
            EpisodeEntity(
                id = 1,
                name = "Pilot",
                airDate = "Dec 2, 2013",
                episode = "S01E01",
                url = null,
                created = null
            ),
            EpisodeEntity(
                id = 2,
                name = "Lawnmower Dog",
                airDate = "Dec 9, 2013",
                episode = "S01E02",
                url = null,
                created = null
            ),
            EpisodeEntity(
                id = 3,
                name = "Anatomy Park",
                airDate = "Dec 16, 2013",
                episode = "S01E03",
                url = null,
                created = null
            )
        )

        episodeDao.insertEpisodes(episodes)
        val result = episodeDao.getEpisodes()

        assertEquals(3, result.size)

        val pilot = result[0]
        val lawnmowerDog = result[1]
        val anatomyPark = result[2]

        assertEquals("Pilot", pilot.name)
        assertEquals("Dec 2, 2013", pilot.airDate)
        assertEquals("S01E01", pilot.episode)
        assertNull(null, pilot.url)
        assertNull(null, pilot.created)

        assertEquals("Lawnmower Dog", lawnmowerDog.name)
        assertEquals("Dec 9, 2013", lawnmowerDog.airDate)
        assertEquals("S01E02", lawnmowerDog.episode)
        assertNull(null, pilot.url)
        assertNull(null, pilot.created)

        assertEquals("Anatomy Park", anatomyPark.name)
        assertEquals("Dec 16, 2013", anatomyPark.airDate)
        assertEquals("S01E03", anatomyPark.episode)
        assertNull(null, pilot.url)
        assertNull(null, pilot.created)
    }

    // ------------------------------------------------------------------------
    // TEST 4: Inserts an episode, then replace existing episode (OnConflictStrategy.REPLACE),
    // and checks that getEpisodeById(id) returns the correct record with the expected values.
    // ------------------------------------------------------------------------
    @Test
    fun insertEpisode_replacesExistingOnConflict() = runBlocking {
        val original = EpisodeEntity(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01",
            url = null,
            created = null
        )

        val updated = EpisodeEntity(
            id = 1,
            name = "Lawnmower Dog",
            airDate = "December 9, 2013",
            episode = "S01E02",
            url = null,
            created = null
        )

        episodeDao.insertEpisode(original)
        episodeDao.insertEpisode(updated)

        val result = episodeDao.getEpisodeById(1)

        assertEquals("Lawnmower Dog", result?.name)
        assertEquals("December 9, 2013", result?.airDate)
        assertEquals("S01E02", result?.episode)
        assertEquals(null, result?.url)
        assertEquals(null, result?.created)
    }

    // ------------------------------------------------------------------------
    // TEST 5: Inserts a list of episodes, delete all records from the table, and ensures the database
    // is empty.
    // ------------------------------------------------------------------------
    @Test
    fun clearAllEpisodes() = runBlocking {
        val episodes = listOf(
            EpisodeEntity(
                id = 1,
                name = "Pilot",
                airDate = "Dec 2, 2013",
                episode = "S01E01",
                url = null,
                created = null
            ),
            EpisodeEntity(
                id = 2,
                name = "Lawnmower Dog",
                airDate = "Dec 9, 2013",
                episode = "S01E02",
                url = null,
                created = null
            )
        )

        episodeDao.insertEpisodes(episodes)
        episodeDao.clearAllEpisodes()

        val result = episodeDao.getEpisodes()
        assertEquals(0, result.size)
    }

    // ------------------------------------------------------------------------
    // TEST 6: Checks that getCharacterById(id) returns null.
    // ------------------------------------------------------------------------
    @Test
    fun getCharacterById_returnsNull_whenNotFound() = runBlocking {
        val result = episodeDao.getEpisodeById(999)
        assertNull(result)
    }
}