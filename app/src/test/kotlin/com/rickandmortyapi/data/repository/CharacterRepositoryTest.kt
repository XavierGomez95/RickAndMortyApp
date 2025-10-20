package com.rickandmortyapi.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.rickandmortyapi.data.database.dao.CharacterDao
import com.rickandmortyapi.data.database.entities.CharacterEntity
import com.rickandmortyapi.data.model.CharacterModel
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterRepositoryTest {

    private lateinit var repository: CharacterRepository

    private val apiService: RickAndMortyApiService = mockk()
    private val characterDao: CharacterDao = mockk()
    private val context: Context = mockk()
    private val sharedPreferences: SharedPreferences = mockk()
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    private val fakeCharacterList = listOf(
        CharacterModel(1, "Rick Sanchez", "https://rickandmortyapi.com/api/character/avatar/1.jpeg", "Alive", "Male", "Human"),
        CharacterModel(2, "Morty Smith", "https://rickandmortyapi.com/api/character/avatar/2.jpeg", "Alive", "Male", "Human")
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { context.getSharedPreferences(any(), any()) } returns sharedPreferences
        every { sharedPreferences.getLong(any(), any()) } returns 0L
        every { sharedPreferences.edit() } returns editor

        coEvery { characterDao.getCharacters() } returns emptyList()
        coEvery { characterDao.clearAllCharacters() } just Runs
        coEvery { characterDao.insertCharacters(any()) } just Runs
        coEvery { characterDao.getCharacterById(any()) } returns null
        every { sharedPreferences.getLong(any(), any()) } returns 0L

        repository = CharacterRepository(apiService, characterDao, context)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    // -------------------------------
    // TEST 1: Success from API (cache expired or empty)
    // -------------------------------
    @Test
    fun `retrieveAllCharacters should return Success when API returns data`() = runTest(testDispatcher) {
        val fakeApiResponse = ApiResponse(
            info = ApiResponseInfo(pages = 1, count = 2, next = null, prev = null),
            results = fakeCharacterList
        )

        coEvery { apiService.getCharacterBatch(1) } returns fakeApiResponse

        val result = repository.retrieveAllCharacters()

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals(2, data.size)
        assertEquals("Rick Sanchez", data[0].name)
        coVerify(exactly = 1) { apiService.getCharacterBatch(1) }
        coVerify { characterDao.clearAllCharacters() }
        coVerify { characterDao.insertCharacters(any()) }
    }

    // -------------------------------
    // TEST 2: Success from local database (valid cache)
    // -------------------------------
    @Test
    fun `retrieveAllCharacters should return local data when cache is valid`() = runTest(testDispatcher) {

        every { sharedPreferences.getLong(any(), any()) } returns System.currentTimeMillis()

        coEvery { characterDao.getCharacters() } returns fakeCharacterList.map {
            CharacterEntity(
                id = it.id,
                name = it.name,
                status = it.status,
                species = it.species,
                gender = it.gender,
                image = it.image
            )
        }

        val result = repository.retrieveAllCharacters()

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals("Morty Smith", data[1].name)

        coVerify(exactly = 0) { apiService.getCharacterBatch(any()) }
    }

    // -------------------------------
    // TEST 3: Failure if API throws exception
    // -------------------------------
    @Test
    fun `retrieveAllCharacters should return Failure on API exception`() = runTest(testDispatcher) {
        coEvery { apiService.getCharacterBatch(any()) } throws RuntimeException("Network error")

        val result = repository.retrieveAllCharacters()

        assertTrue(result is Resource.Failure)
    }

    // -------------------------------
    // TEST 4: getCharacterById returns from local database
    // -------------------------------
    @Test
    fun `getCharacterById should return Success from local`() = runTest(testDispatcher) {
        val localCharacter = CharacterEntity(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        )
        coEvery { characterDao.getCharacterById(1) } returns localCharacter

        val result = repository.getCharacterById(1)

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals("Rick Sanchez", data.name)
        coVerify(exactly = 1) { characterDao.getCharacterById(1) }
        coVerify(exactly = 0) { apiService.getCharacterById(any()) }
    }

    // -------------------------------
    // TEST 5: getCharacterById returns from API if not in DB (this test is unreacheable in real life)
    // -------------------------------
    @Test
    fun `getCharacterById should fetch from API if not local`() = runTest(testDispatcher) {
        coEvery { characterDao.getCharacterById(1) } returns null
        coEvery { apiService.getCharacterById(1) } returns fakeCharacterList[0]
        coEvery { characterDao.insertCharacter(any()) } just Runs

        val result = repository.getCharacterById(1)

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals("Rick Sanchez", data.name)
        coVerify { apiService.getCharacterById(1) }
        coVerify { characterDao.insertCharacter(any()) }
    }

    // -------------------------------
    // TEST 6: getCharacterById retorna Failure si API falla
    // -------------------------------
    @Test
    fun `getCharacterById should return Failure on exception`() = runTest(testDispatcher) {
        coEvery { characterDao.getCharacterById(1) } throws RuntimeException("DB error")

        val result = repository.getCharacterById(1)

        assertTrue(result is Resource.Failure)
    }
}
