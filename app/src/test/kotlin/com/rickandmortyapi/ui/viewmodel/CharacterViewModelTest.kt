package com.rickandmortyapi.ui.viewmodel

import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.repository.interfaces.CharacterRepositoryInterface
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
class CharacterViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: CharacterViewModel // Late initialization
    private lateinit var fakeRepository: FakeCharacterRepository // Late initialization

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeCharacterRepository()
        viewModel = CharacterViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ------------------------------------------------------------------------
    // TEST 1: Verifies that fetchCharacters() emits a Resource.Success state
    // when FakeCharacterRepository returns data successfully.
    // ------------------------------------------------------------------------
    @Test
    fun `fetchCharacters should emit Success with FakeRepository`() = runTest {
        viewModel.fetchCharacters()
        advanceUntilIdle()

        val result = viewModel.characterModelStateFlow.first()
        assertTrue(result is Resource.Success)
        assertEquals(2, (result as Resource.Success).data.size)
    }

    // ------------------------------------------------------------------------
    // TEST 2: Ensures that fetchCharacters() emits a Resource.Failure state
    // when FakeCharacterRepository is set to fail (shouldFail = true).
    // ------------------------------------------------------------------------
    @Test
    fun `fetchCharacters should emit Failure on error`() = testScope.runTest { // controlled coroutine part 1
        fakeRepository.shouldFail = true

        viewModel.fetchCharacters()
        advanceUntilIdle()

        val result = viewModel.characterModelStateFlow.first()
        assertTrue(result is Resource.Failure)
    }

    // ------------------------------------------------------------------------
    // TEST 3: Validates that getCharacterById(id)  Resource.Success with
    // the correct CharacterModel data.
    // ------------------------------------------------------------------------
    @Test
    fun `getCharacterById should emit Success`() = testScope.runTest { // controlled coroutine part 2
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

    // ------------------------------------------------------------------------
    // TEST 4: Ensures that getCharacterById(id) emits a Resource.Failure
    // when the requested character ID is not found in the repository.
    // ------------------------------------------------------------------------
    @Test
    fun `getCharacterById should emit Failure when character not found`() = testScope.runTest { // controlled coroutine part 3
        viewModel.getCharacterById(999)
        advanceUntilIdle()

        val result = viewModel.singleCharacterStateFlow.first()
        assertTrue(result is Resource.Failure)
    }
}

class FakeCharacterRepository : CharacterRepositoryInterface {
    var shouldFail = false
    val fakeCharacters = listOf(
        CharacterModel(
            id = 1,
            name = "Rick Sanchez",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            status = "Alive",
            gender = "Male",
            species = "Human"
        ),
        CharacterModel(
            id = 2,
            name = "Morty Smith",
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            status = "Alive",
            gender = "Male",
            species = "Human"
        )
    )

    override suspend fun retrieveAllCharacters(): Resource<List<CharacterModel>> {
        return if (shouldFail) Resource.Failure else Resource.Success(fakeCharacters)
    }

    override suspend fun getCharacterById(id: Int): Resource<CharacterModel> {
        val character = fakeCharacters.find { it.id == id }
        return character?.let { Resource.Success(it) } ?: Resource.Failure
    }
}