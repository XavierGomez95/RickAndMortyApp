package com.rickandmortyapi.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.rickandmortyapi.data.database.dao.EpisodeDao
import com.rickandmortyapi.data.database.entities.EpisodeEntity
import com.rickandmortyapi.data.model.EpisodeModel
import com.rickandmortyapi.data.model.repository.ApiResponse
import com.rickandmortyapi.data.model.repository.ApiResponseInfo
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EpisodeRepositoryTest {
    private lateinit var repository: EpisodeRepository

    private val apiService: RickAndMortyApiService = mockk()
    private val episodeDao: EpisodeDao = mockk()
    private val context: Context = mockk()
    private val sharedPreferences: SharedPreferences = mockk()
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    private val fakeEpisodeList = listOf(
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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { context.getSharedPreferences(any(), any()) } returns sharedPreferences
        every { sharedPreferences.getLong(any(), any()) } returns 0L
        every { sharedPreferences.edit() } returns editor

        coEvery { episodeDao.getEpisodes() } returns emptyList()
        coEvery { episodeDao.clearAllEpisodes() } just Runs
        coEvery { episodeDao.insertEpisodes(any()) } just Runs
        coEvery { episodeDao.getEpisodeById(any()) } returns null
        every { sharedPreferences.getLong(any(), any()) } returns 0L

        repository = EpisodeRepository(apiService, episodeDao, context)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    // -------------------------------
    // TEST 1: Success from API (cache expired or empty)
    // -------------------------------
    @Test
    fun `retrieveAllEpisodes should return Success when API returns data`() = runTest(testDispatcher) {
        val fakeApiResponse = ApiResponse(
            info = ApiResponseInfo(pages = 1, count = 2, next = null, prev = null),
            results = fakeEpisodeList
        )

        coEvery { apiService.getEpisodeBatch(1) } returns fakeApiResponse

        val result = repository.retrieveAllEpisodes()

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals(2, data.size)
        assertEquals("Pilot", data[0].name)
        coVerify(exactly = 1) { apiService.getEpisodeBatch(1) }
        coVerify { episodeDao.clearAllEpisodes() }
        coVerify { episodeDao.insertEpisodes(any()) }
    }

    // -------------------------------
    // TEST 2: Success from local database (valid cache)
    // -------------------------------
    @Test
    fun `retrieveAllEpisodes should return local data when cache is valid`() = runTest(testDispatcher) {
        every { sharedPreferences.getLong(any(), any()) } returns System.currentTimeMillis()

        coEvery { episodeDao.getEpisodes() } returns fakeEpisodeList.map {
            EpisodeEntity(
                id = it.id,
                name = it.name,
                airDate = it.airDate,
                episode = it.episode,
                url = null,
                created = null
            )
        }

        val result = repository.retrieveAllEpisodes()

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals("Lawnmower Dog", data[1].name)

        coVerify(exactly = 0) { apiService.getEpisodeBatch(any()) }
    }

    // -------------------------------
    // TEST 3: Failure if API throws exception
    // -------------------------------
    @Test
    fun `retrieveAllEpisodes should return Failure on API exception`() = runTest(testDispatcher) {
        coEvery { apiService.getEpisodeBatch(any()) } throws RuntimeException("Network error")

        val result = repository.retrieveAllEpisodes()

        assertTrue(result is Resource.Failure)
    }

    // -------------------------------
    // TEST 4: getEpisodeById returns from local database
    // -------------------------------
    @Test
    fun `getEpisodeById should return Success from local`() = runTest(testDispatcher) {
        val localEpisode = EpisodeEntity(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01",
            url = null,
            created = null
        )
        coEvery { episodeDao.getEpisodeById(1) } returns localEpisode

        val result = repository.getEpisodeById(1)

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals("Pilot", data.name)
        coVerify(exactly = 1) { episodeDao.getEpisodeById(1) }
        coVerify(exactly = 0) { apiService.getEpisodeById(any()) }
    }

    // -------------------------------
    // TEST 5: getEpisodeById returns from API if not found locally
    // -------------------------------
    @Test
    fun `getEpisodeById should fetch from API if not local`() = runTest(testDispatcher) {
        coEvery { episodeDao.getEpisodeById(1) } returns null
        coEvery { apiService.getEpisodeById(1) } returns fakeEpisodeList[0]
        coEvery { episodeDao.insertEpisode(any()) } just Runs

        val result = repository.getEpisodeById(1)

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals("Pilot", data.name)
        coVerify { apiService.getEpisodeById(1) }
        coVerify { episodeDao.insertEpisode(any()) }
    }

    // -------------------------------
    // TEST 6: getEpisodeById returns Failure if DAO or API fails
    // -------------------------------
    @Test
    fun `getEpisodeById should return Failure on exception`() = runTest(testDispatcher) {
        coEvery { episodeDao.getEpisodeById(1) } throws RuntimeException("DB error")

        val result = repository.getEpisodeById(1)

        assertTrue(result is Resource.Failure)
    }
}