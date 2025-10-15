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

    @Test
    fun `fetchEpisodes should emit Loading then Success`() = testScope.runTest {
        viewModel.fetchEpisodes()
        advanceUntilIdle()

        val result = viewModel.episodes.first()
        assertTrue(result is Resource.Success)

        val data = (result as Resource.Success).data
        assertEquals(2, data.size)
        assertEquals("Pilot", data[0].name)
        assertEquals("Lawnmower Dog", data[1].name)
    }

    @Test
    fun `fetchEpisodes should emit Failure when repository fails`() = testScope.runTest {
        fakeRepository.shouldFail = true

        viewModel.fetchEpisodes()
        advanceUntilIdle()

        val result = viewModel.episodes.first()
        assertTrue(result is Resource.Failure)
    }

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
        EpisodeModel(1, "Pilot", "December 2, 2013", "S03E07"),
        EpisodeModel(2, "Lawnmower Dog", "December 9, 2013", "S01E02")
    )

    override suspend fun retrieveAllEpisodes(): Resource<List<EpisodeModel>> {
        return if (shouldFail) Resource.Failure else Resource.Success(fakeEpisodes)
    }

    override suspend fun getEpisodeById(id: Int): Resource<EpisodeModel> {
        val character = fakeEpisodes.find { it.id == id }
        return character?.let { Resource.Success(it) } ?: Resource.Failure
    }
}