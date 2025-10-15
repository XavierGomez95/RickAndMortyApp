package com.rickandmortyapi.ui.viewmodel


import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.repository.interfaces.CharacterRepositoryInterface
import com.rickandmortyapi.data.utils.Resource
import io.mockk.coEvery
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
class CharacterViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: CharacterViewModel
    private lateinit var fakeRepository: FakeCharacterRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CharacterViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCharacters should emit Success with FakeRepository`() = runTest {
        val fakeRepository = FakeCharacterRepository()
        val viewModel = CharacterViewModel(fakeRepository)

        viewModel.fetchCharacters()
        advanceUntilIdle()

        val result = viewModel.characterModelStateFlow.first()
        assertTrue(result is Resource.Success)
        assertEquals(2, (result as Resource.Success).data.size)
    }

    @Test
    fun `fetchCharacters should emit Failure on error`() = testScope.runTest {
        coEvery { fakeRepository.retrieveAllCharacters() } returns Resource.Failure

        viewModel.fetchCharacters()
        advanceUntilIdle()

        val result = viewModel.characterModelStateFlow.first()
        assertTrue(result is Resource.Failure)
    }

    @Test
    fun `getCharacterById should emit Loading then Success`() = testScope.runTest {
        val fakeRepository = FakeCharacterRepository()
        val viewModel = CharacterViewModel(fakeRepository)

        viewModel.getCharacterById(1)
        advanceUntilIdle()

        val result = viewModel.singleCharacterStateFlow.first()

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals(1, data.id)
        assertEquals("Rick Sanchez", data.name)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/1.jpeg", data.image)
        assertEquals("Alive", data.status)
        assertEquals("Male", data.gender)
        assertEquals("Human", data.species)
    }

    @Test
    fun `getCharacterById should emit Failure when character not found`() = testScope.runTest {
        val fakeRepository = FakeCharacterRepository()
        val viewModel = CharacterViewModel(fakeRepository)

        viewModel.getCharacterById(999)
        advanceUntilIdle()

        val result = viewModel.singleCharacterStateFlow.first()
        assertTrue(result is Resource.Failure)
    }
}

class FakeCharacterRepository : CharacterRepositoryInterface {
    val shouldFail = false
    val fakeCharacters = listOf(
        CharacterModel(1, "Rick Sanchez", "https://rickandmortyapi.com/api/character/avatar/1.jpeg", "Alive", "Male", "Human"),
        CharacterModel(2, "Morty Smith", "https://rickandmortyapi.com/api/character/avatar/2.jpeg", "Alive", "Male", "Human")
    )

    override suspend fun retrieveAllCharacters(): Resource<List<CharacterModel>> {
        return if (shouldFail) Resource.Failure else Resource.Success(fakeCharacters)
    }

    override suspend fun getCharacterById(id: Int): Resource<CharacterModel> {
        val character = fakeCharacters.find { it.id == id }
        return character?.let { Resource.Success(it) } ?: Resource.Failure
    }
}