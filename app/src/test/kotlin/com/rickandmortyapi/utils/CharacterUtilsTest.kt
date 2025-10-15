package com.rickandmortyapi.utils

import com.rickandmortyapi.data.database.entities.CharacterEntity
import com.rickandmortyapi.data.model.CharacterModel
import org.junit.Assert.*
import org.junit.Test

class CharacterUtilsTest {
    @Test
    fun `characterEntityToModel should correctly map fields`() {
        val entity = CharacterEntity(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            gender = "Male"
        )

        val model = characterEntityToModel(entity)

        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.status, model.status)
        assertEquals(entity.species, model.species)
        assertEquals(entity.image, model.image)
        assertEquals(entity.gender, model.gender)

        assertEquals(1, model.id)
        assertEquals("Rick Sanchez", model.name)
        assertEquals("Alive", model.status)
        assertEquals("Human", model.species)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/1.jpeg", model.image)
        assertEquals("Male", model.gender)
    }

    @Test
    fun `characterModelToEntity should correctly map fields`() {
        val model = CharacterModel(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            gender = "Male"
        )

        val entity = characterModelToEntity(model)

        assertEquals(model.id, entity.id)
        assertEquals(model.name, entity.name)
        assertEquals(model.status, entity.status)
        assertEquals(model.species, entity.species)
        assertEquals(model.image, entity.image)
        assertEquals(model.gender, entity.gender)



        assertEquals(2, entity.id)
        assertEquals("Morty Smith", entity.name)
        assertEquals("Alive", entity.status)
        assertEquals("Human", entity.species)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/2.jpeg", entity.image)
        assertEquals("Male", entity.gender)
    }

    @Test
    fun `entity to model and back should preserve data integrity`() {
        val originalEntity = CharacterEntity(
            id = 3,
            name = "Summer Smith",
            status = "Alive",
            species = "Human",
            image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
            gender = "Female"
        )

        val convertedModel = characterEntityToModel(originalEntity)
        val convertedEntity = characterModelToEntity(convertedModel)

        assertEquals(originalEntity, convertedEntity)
    }

    @Test
    fun `model to entity and back should preserve data integrity`() {
        val originalModel = CharacterModel(
            id = 4,
            name = "Beth Smith",
            status = "Alive",
            species = "Human",
            image = "https://rickandmortyapi.com/api/character/avatar/4.jpeg",
            gender = "Female"
        )

        val convertedEntity = characterModelToEntity(originalModel)
        val convertedModel = characterEntityToModel(convertedEntity)

        assertEquals(originalModel, convertedModel)
    }
}