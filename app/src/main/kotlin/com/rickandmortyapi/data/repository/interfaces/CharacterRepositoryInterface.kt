package com.rickandmortyapi.data.repository.interfaces

import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.utils.Resource

interface CharacterRepositoryInterface {
    suspend fun retrieveAllCharacters(): Resource<List<CharacterModel>>
    suspend fun getCharacterById(id: Int): Resource<CharacterModel>
}