package com.rickandmortyapi.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rickandmortyapi.data.database.AppDatabase
import com.rickandmortyapi.data.database.entities.CharacterEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CharacterDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var characterDao: CharacterDao

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        characterDao = appDatabase.characterDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    // ------------------------------------------------------------------------
    // TEST 1: Inserts a character into the database and checks that getCharacters() returns
    // the correct record with the expected values.
    // ------------------------------------------------------------------------
    @Test
    fun insertAndRetrieveCharacters() = runBlocking {
        val character = CharacterEntity(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        )

        characterDao.insertCharacter(character)
        val result = characterDao.getCharacters()

        val firstCharacter = result.first()

        assertEquals(1, result.size)
        assertEquals("Rick Sanchez", firstCharacter.name)
        assertEquals("Alive", firstCharacter.status)
        assertEquals("Human", firstCharacter.species)
        assertEquals("Male", firstCharacter.gender)
        assertEquals(
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            firstCharacter.image
        )
    }

    // ------------------------------------------------------------------------
    // TEST 2: Inserts a character and checks that getCharacterById(id) returns the correct record
    // with the expected values.
    // ------------------------------------------------------------------------
    @Test
    fun insertAndRetrieveSingleCharacter() = runBlocking {
        val character = CharacterEntity(
            id = 1,
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
        )

        characterDao.insertCharacter(character)
        val result = characterDao.getCharacterById(1)

        assertEquals("Morty Smith", result?.name)
        assertEquals("Alive", result?.status)
        assertEquals("Human", result?.species)
        assertEquals("Male", result?.gender)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/2.jpeg", result?.image)
    }

    // ------------------------------------------------------------------------
    // TEST 3: Insert a list of characters and ensures the data retrieved is correct.
    // ------------------------------------------------------------------------
    @Test
    fun insertMultipleCharacters_andRetrieveAll() = runBlocking {
        val characters = listOf(
            CharacterEntity(
                id = 1,
                name = "Rick Sanchez",
                species = "Alive",
                status = "Human",
                gender = "Male",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
            ),
            CharacterEntity(
                id = 2,
                name = "Morty Smith",
                species = "Alive",
                status = "Human",
                gender = "Male",
                image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
            ),
            CharacterEntity(
                id = 3,
                name = "Summer Smith",
                species = "Alive",
                status = "Human",
                gender = "Female",
                image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg"
            )
        )

        characterDao.insertCharacters(characters)
        val result = characterDao.getCharacters()

        assertEquals(3, result.size)

        val rick = result[0]
        val morty = result[1]
        val summer = result[2]

        assertEquals("Rick Sanchez", rick.name)
        assertEquals("Alive", rick.species)
        assertEquals("Human", rick.status)
        assertEquals("Male", rick.gender)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            rick.image)


        assertEquals("Morty Smith", morty.name)
        assertEquals("Alive", morty.species)
        assertEquals("Human", morty.status)
        assertEquals("Male", morty.gender)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            morty.image)


        assertEquals("Summer Smith", summer.name)
        assertEquals("Alive", summer.species)
        assertEquals("Human", summer.status)
        assertEquals("Female", summer.gender)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/3.jpeg",
            summer.image)
    }

    // ------------------------------------------------------------------------
    // TEST 4: Inserts a character, then replace existing character (OnConflictStrategy.REPLACE),
    // and checks that getCharacterById(id) returns the correct record with the expected values.
    // ------------------------------------------------------------------------
    @Test
    fun insertCharacter_replacesExistingOnConflict() = runBlocking {
        val original = CharacterEntity(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        )

        val updated = CharacterEntity(
            id = 1,
            name = "Adjudicator Rick",
            status = "Dead",
            species = "Human",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/8.jpeg"
        )

        characterDao.insertCharacter(original)
        characterDao.insertCharacter(updated)

        val result = characterDao.getCharacterById(1)

        assertEquals("Adjudicator Rick", result?.name)
        assertEquals("Dead", result?.status)
        assertEquals("Human", result?.species)
        assertEquals("Male", result?.gender)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/8.jpeg", result?.image)
    }

    // ------------------------------------------------------------------------
    // TEST 5: Inserts a list of characters, delete all records from the table, and ensures the
    // database is empty.
    // ------------------------------------------------------------------------
    @Test
    fun clearAllCharacters() = runBlocking {
        val characters = listOf(
            CharacterEntity(
                id = 1,
                name = "Rick Sanchez",
                image = "Alive",
                status = "Human",
                gender = "Male",
                species = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
            ),
            CharacterEntity(
                id = 2,
                name = "Morty Smith",
                image = "Alive",
                status = "Human",
                gender = "Male",
                species = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
            )
        )

        characterDao.insertCharacters(characters)
        characterDao.clearAllCharacters()

        val result = characterDao.getCharacters()
        assertEquals(0, result.size)
    }

    // ------------------------------------------------------------------------
    // TEST 6: Checks that getCharacterById(id) returns null.
    // ------------------------------------------------------------------------
    @Test
    fun getCharacterById_returnsNull_whenNotFound() = runBlocking {
        val result = characterDao.getCharacterById(999)
        assertNull(result)
    }
}