package com.rickandmortyapi.data.repository

import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.repository.interfaces.CharacterRepositoryInterface
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource
import javax.inject.Inject


class CharacterRepository @Inject constructor(
    private val apiService: RickAndMortyApiService
) : CharacterRepositoryInterface {
    override suspend fun retrieveAllCharacters(): Resource<List<CharacterModel>> {
        try {
            val allCharacterData = mutableListOf<CharacterModel>()

            val firstPage = 1
            val firstCharacterBatch = apiService.getCharacterBatch(firstPage)
            allCharacterData.addAll(firstCharacterBatch.results)

            val totalPages = firstCharacterBatch.info.pages
            for (currentPage in 2 .. totalPages) {
                val charactersBatch = apiService.getCharacterBatch(currentPage)
                allCharacterData.addAll(charactersBatch.results)
            }

            return Resource.Success(allCharacterData)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Failure
        }
    }

    override suspend fun retrieveCharactersById(intArray: IntArray): Resource<List<CharacterModel>> {
        try {
            val specifiedCharacterData = mutableListOf<CharacterModel>()

            val totalCharacters = intArray.size
            for (index in 0 until totalCharacters) {
                val currentCharacter = apiService.getCharacterById(intArray[index])
                specifiedCharacterData.addAll(currentCharacter)
            }

            return Resource.Success(specifiedCharacterData)
        } catch (e: Exception) {
            e.stackTrace
            return Resource.Failure
        }
    }
}