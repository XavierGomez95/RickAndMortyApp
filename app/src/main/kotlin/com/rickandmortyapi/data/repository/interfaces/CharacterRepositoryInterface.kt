package com.rickandmortyapi.data.repository.interfaces

import com.rickandmortyapi.data.model.Character
import com.rickandmortyapi.data.utils.Resource

interface CharacterRepositoryInterface {
    suspend fun retrieveAllCharacters(): Resource<List<Character>>

    suspend fun retrieveCharactersById(intArray: IntArray): Resource<List<Character>>
}