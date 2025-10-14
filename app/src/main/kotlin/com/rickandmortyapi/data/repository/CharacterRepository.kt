package com.rickandmortyapi.data.repository

import android.content.Context
import android.util.Log
import com.rickandmortyapi.data.database.dao.CharacterDao
import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.repository.interfaces.CharacterRepositoryInterface
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource
import com.rickandmortyapi.utils.characterEntityToModel
import com.rickandmortyapi.utils.characterModelToEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CharacterRepository @Inject constructor(
    private val apiService: RickAndMortyApiService,
    private val characterDao: CharacterDao,
    @ApplicationContext private val context: Context
) : CharacterRepositoryInterface {
    override suspend fun retrieveAllCharacters(): Resource<List<CharacterModel>> {
        try {
            val localCharactersData = withContext(Dispatchers.IO) {
                characterDao.getCharacters()
            }
            if (localCharactersData.isNotEmpty()) {
                return Resource.Success(localCharactersData.map { characterEntityToModel(it) })
            }

            val allCharacterData = mutableListOf<CharacterModel>()

            val firstPage = 1
            val firstCharacterBatch = apiService.getCharacterBatch(firstPage)
            allCharacterData.addAll(firstCharacterBatch.results)

            val totalPages = firstCharacterBatch.info.pages
            for (currentPage in 2..totalPages) {
                val charactersBatch = apiService.getCharacterBatch(currentPage)
                allCharacterData.addAll(charactersBatch.results)
            }

            withContext(Dispatchers.IO) {
                characterDao.insertCharacters(allCharacterData.map { characterModelToEntity(it) })
            }

            return Resource.Success(allCharacterData)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Failure
        }
    }

    override suspend fun getCharacterById(id: Int): Resource<CharacterModel> {
        try {
            val localCharacterEntity = withContext(Dispatchers.IO) {
                characterDao.getCharacterById(id)
            }
            if (localCharacterEntity != null) {
                return Resource.Success(characterEntityToModel(localCharacterEntity))
            }

            val characterFromApi = apiService.getCharacterById(id)
            withContext(Dispatchers.IO) {
                characterDao.insertCharacter(characterModelToEntity(characterFromApi))
            }
            return Resource.Success(characterFromApi)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ÇÇÇÇ__CharacterRepository", "Error getting character by id", e)
            return Resource.Failure
        }
    }
}