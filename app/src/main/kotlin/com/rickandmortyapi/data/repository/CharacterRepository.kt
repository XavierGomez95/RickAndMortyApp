package com.rickandmortyapi.data.repository

import android.util.Log
import com.rickandmortyapi.data.database.dao.CharacterDao
import com.rickandmortyapi.data.database.entities.CharacterEntity
import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.repository.interfaces.CharacterRepositoryInterface
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CharacterRepository @Inject constructor(
    private val apiService: RickAndMortyApiService,
    private val characterDao: CharacterDao
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


    fun getCharacters(): List<CharacterEntity> = characterDao.getCharacter()

    override suspend fun getCharacterById(id: Int): Resource<CharacterModel> {
        try {
            val localCharacterEntity = withContext(Dispatchers.IO) {
                characterDao.getCharacterById(id)
            }
            if (localCharacterEntity != null) {
                val localCharacter = CharacterModel(
                    id = localCharacterEntity.id,
                    name = localCharacterEntity.name,
                    status = localCharacterEntity.status,
                    species = localCharacterEntity.species,
                    image = localCharacterEntity.image,
                    gender = localCharacterEntity.gender
                )
                return Resource.Success(localCharacter)
            }

            val characterFromApi = apiService.getCharacterById(id)
            withContext(Dispatchers.IO) {
                characterDao.insertCharacter(
                    CharacterEntity(
                        id = characterFromApi.id,
                        name = characterFromApi.name,
                        status = characterFromApi.status,
                        species = characterFromApi.species,
                        image = characterFromApi.image,
                        gender = characterFromApi.gender
                    )
                )
            }
            return Resource.Success(characterFromApi)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ÇÇÇÇ__CharacterRepository", "Error getting character by id", e)
            return Resource.Failure
        }
    }
}