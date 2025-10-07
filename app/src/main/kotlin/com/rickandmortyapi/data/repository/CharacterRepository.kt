package com.rickandmortyapi.data.repository

import com.rickandmortyapi.data.model.Character
import com.rickandmortyapi.data.repository.interfaces.CharacterRepositoryInterface
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource

class CharacterRepository (
    private val apiService: RickAndMortyApiService
) : CharacterRepositoryInterface {
    override suspend fun retrieveAllCharacters(): Resource<List<Character>> {
        try {
            val allCharacters = mutableListOf<Character>()

            val firstPage = 1
            val firstCharacterBatch = apiService.getCharacterBatch(firstPage)
            allCharacters.addAll(firstCharacterBatch.results)

            val totalPages = firstCharacterBatch.info.pages
            for (currentPage in 2 .. totalPages) {
                val charactersBatch = apiService.getCharacterBatch(currentPage)
                allCharacters.addAll(charactersBatch.results)
            }

            return Resource.Success(allCharacters)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Failure
        }
    }

    override suspend fun retrieveCharactersById(intArray: IntArray): Resource<List<Character>> {
        try {
            val specifiedCharacters = mutableListOf<Character>()

            val totalCharacters = intArray.size
            for (index in 0 until totalCharacters) {
                val currentCharacter = apiService.getCharacterById(intArray[index])
                specifiedCharacters.addAll(currentCharacter)
            }

            return Resource.Success(specifiedCharacters)
        } catch (e: Exception) {
            e.stackTrace
            return Resource.Failure
        }

    }
}