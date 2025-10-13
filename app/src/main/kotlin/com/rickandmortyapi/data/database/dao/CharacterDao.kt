package com.rickandmortyapi.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rickandmortyapi.data.database.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character_table")
    fun getCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM character_table WHERE id = :id")
    fun getCharacterById(id: Int): CharacterEntity?

    @Insert
    suspend fun insertCharacters(character: List<CharacterEntity>)

    @Insert
    suspend fun insertCharacter(character: CharacterEntity)

    @Query("DELETE FROM character_table")
    suspend fun clearAll()
}