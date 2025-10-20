package com.rickandmortyapi.ui.viewmodel

import com.rickandmortyapi.data.model.EpisodeModel
import com.rickandmortyapi.data.repository.interfaces.EpisodeRepositoryInterface
import com.rickandmortyapi.data.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EpisodeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: EpisodeViewModel
    private lateinit var fakeRepository: FakeEpisodeRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeEpisodeRepository()
        viewModel = EpisodeViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ------------------------------------------------------------------------
    // TEST 1: Verifies that fetchEpisodes() emits a Resource.Success state
    // when FakeEpisodeRepository returns data successfully.
    // ------------------------------------------------------------------------
    @Test
    fun `fetchEpisodes should emit Loading then Success`() = testScope.runTest {
        viewModel.fetchEpisodes()
        advanceUntilIdle()

        val result = viewModel.episodes.first()
        assertTrue(result is Resource.Success)

        val data = (result as Resource.Success).data

        val firstEpisode = data[0]
        val secondEpisode = data[1]

        assertEquals(2, data.size)
        assertEquals("Pilot", firstEpisode.name)
        assertEquals("Lawnmower Dog", secondEpisode.name)
    }

    // ------------------------------------------------------------------------
    // TEST 2: Ensures that fetchEpisodes() emits a Resource.Failure state
    // when FakeEpisodeRepository is set to fail (shouldFail = true).
    // ------------------------------------------------------------------------
    @Test
    fun `fetchEpisodes should emit Failure when repository fails`() = testScope.runTest {
        fakeRepository.shouldFail = true

        viewModel.fetchEpisodes()
        advanceUntilIdle()

        val result = viewModel.episodes.first()
        assertTrue(result is Resource.Failure)
    }

    // ------------------------------------------------------------------------
    // TEST 3: Validates that getEpisodeById(id) emits a Resource.Success state
    // and that the returned UI model contains correctly formatted data.
    // ------------------------------------------------------------------------
    @Test
    fun `getEpisodeById should emit formatted UI data when Success`() = testScope.runTest {
        viewModel.getEpisodeById(1)
        advanceUntilIdle()

        val result = viewModel.singleEpisode.first()
        assertTrue(result is Resource.Success)

        val uiEpisode = (result as Resource.Success).data
        assertEquals("December 2, 2013", uiEpisode.uiDate)
        assertEquals("03", uiEpisode.uiSeason)
        assertEquals("07", uiEpisode.uiEpisode)
    }

    // ------------------------------------------------------------------------
    // TEST 4: Ensures that getEpisodeById(id) emits a Resource.Failure state
    // when the requested episode ID is not found in the repository.
    // ------------------------------------------------------------------------
    @Test
    fun `getEpisodeById should emit Failure when episode not found`() = testScope.runTest {
        viewModel.getEpisodeById(999)
        advanceUntilIdle()

        val result = viewModel.singleEpisode.first()
        assertTrue(result is Resource.Failure)
    }
}

class FakeEpisodeRepository : EpisodeRepositoryInterface {
    var shouldFail = false
    val fakeEpisodes = listOf(
        EpisodeModel(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S03E07"
        ),
        EpisodeModel(
            id = 2,
            name = "Lawnmower Dog",
            airDate = "December 9, 2013",
            episode = "S01E02"
        )
    )

    override suspend fun retrieveAllEpisodes(): Resource<List<EpisodeModel>> {
        return if (shouldFail) Resource.Failure else Resource.Success(fakeEpisodes)
    }

    override suspend fun getEpisodeById(id: Int): Resource<EpisodeModel> {
        val character = fakeEpisodes.find { it.id == id }
        return character?.let { Resource.Success(it) } ?: Resource.Failure
    }
}