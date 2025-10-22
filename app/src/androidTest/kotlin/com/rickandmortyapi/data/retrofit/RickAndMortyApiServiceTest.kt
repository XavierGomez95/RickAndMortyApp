package com.rickandmortyapi.data.retrofit

import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: RickAndMortyApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    // ------------------------------------------------------------------------
    // TEST 1: Simulates a successful API call to getCharacterById()
    // and checks that the returned CharacterModel matches the expected data.
    // ------------------------------------------------------------------------
    @Test
    fun getCharacterById_returnsCharacterModel() = runBlocking {
        val mockJson = loadJsonFromAssets("character_single.json")

        mockWebServer.enqueue(MockResponse().setBody(mockJson).setResponseCode(200))

        val result = api.getCharacterById(1)

        assertEquals(1, result.id)
        assertEquals("Rick Sanchez", result.name)
        assertEquals("Alive", result.status)
        assertEquals("Human", result.species)
        assertEquals("Male", result.gender)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            result.image)

        val request = mockWebServer.takeRequest()
        assertEquals("/character/1", request.path)
    }

    // ------------------------------------------------------------------------
    // TEST 2: Simulates a successful API call to getCharacterBatch()
    // and checks that the returned list of characters contains expected values.
    // ------------------------------------------------------------------------
    @Test
    fun getCharacterBatch_returnsListOfCharacters() = runBlocking {
        val mockJson = loadJsonFromAssets("character_batch.json")

        mockWebServer.enqueue(MockResponse().setBody(mockJson).setResponseCode(200))

        val response = api.getCharacterBatch(1)

        val morty = response.results[1]

        assertEquals(20, response.results.size)
        assertEquals("Morty Smith", morty.name)
        assertEquals("Alive", morty.status)
        assertEquals("Human", morty.species)
        assertEquals("Male", morty.gender)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            morty.image)

        val request = mockWebServer.takeRequest()
        assertEquals("/character?page=1", request.path)
    }

    // ------------------------------------------------------------------------
    // TEST 3: Simulates a successful API call to getEpisodeById()
    // and checks that the returned EpisodeModel matches the expected data.
    // ------------------------------------------------------------------------
    @Test
    fun getEpisodeById_returnsEpisodeModel() = runBlocking {
        val mockJson = loadJsonFromAssets("episode_single.json")

        mockWebServer.enqueue(MockResponse().setBody(mockJson).setResponseCode(200))

        val result = api.getEpisodeById(1)

        assertEquals(1, result.id)
        assertEquals("Pilot", result.name)
        assertEquals("S01E01", result.episode)
        assertEquals("December 2, 2013", result.airDate)

        val request = mockWebServer.takeRequest()
        assertEquals("/episode/1", request.path)
    }

    // ------------------------------------------------------------------------
    // TEST 4: Simulates a successful API call to getEpisodeBatch()
    // and checks that the returned list of episodes contains expected values.
    // ------------------------------------------------------------------------
    @Test
    fun getEpisodeBatch_returnsListOfEpisodes() = runBlocking {
        val mockJson = loadJsonFromAssets("episode_batch.json")

        mockWebServer.enqueue(MockResponse().setBody(mockJson).setResponseCode(200))

        val response = api.getEpisodeBatch(1)

        assertEquals(20, response.results.size)
        assertEquals("Lawnmower Dog", response.results[1].name)
        assertEquals("S01E02", response.results[1].episode)

        val request = mockWebServer.takeRequest()
        assertEquals("/episode?page=1", request.path)
    }

    private fun loadJsonFromAssets(fileName: String): String {
        val context = InstrumentationRegistry.getInstrumentation().context
        val inputStream = context.assets.open(fileName)
        val reader = inputStream.bufferedReader()
        val json = reader.readText()
        reader.close()
        return json
    }
}