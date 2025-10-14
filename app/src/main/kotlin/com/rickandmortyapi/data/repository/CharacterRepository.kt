package com.rickandmortyapi.data.repository

import android.content.Context
import android.util.Log
import com.rickandmortyapi.data.database.dao.CharacterDao
import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.repository.interfaces.CharacterRepositoryInterface
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource
import com.rickandmortyapi.utils.Constants
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
            val sharedPreferences = context.getSharedPreferences("character_sync_preferences", Context.MODE_PRIVATE)
            val lastUpdateTime = sharedPreferences.getLong("last_update_time", 0L)
            val currentTime = System.currentTimeMillis()

            val localCharactersData = withContext(Dispatchers.IO) { // Subprocess out of main thread
                characterDao.getCharacters()
            }

            if (currentTime - lastUpdateTime > Constants.DAY_TIME_MILLIS || localCharactersData.isEmpty()) {
                val allCharacterData = mutableListOf<CharacterModel>()
                val firstPage = 1
                val firstCharacterBatch = apiService.getCharacterBatch(firstPage)
                allCharacterData.addAll(firstCharacterBatch.results)

                val totalPages = firstCharacterBatch.info.pages
                for (currentPage in 2..totalPages) {
                    val charactersBatch = apiService.getCharacterBatch(currentPage)
                    allCharacterData.addAll(charactersBatch.results)
                }

                withContext(Dispatchers.IO) { // Subprocess out of main thread
                    characterDao.clearAllCharacters()
                    characterDao.insertCharacters(allCharacterData.map { characterModelToEntity(it) })
                }

                return Resource.Success(allCharacterData)
            } else {
                val localCharacters = localCharactersData.map { characterEntityToModel(it) }
                return Resource.Success(localCharacters)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Failure
        }
    }

    override suspend fun getCharacterById(id: Int): Resource<CharacterModel> {
        try {
            val localCharacterEntity = withContext(Dispatchers.IO) { // Subprocess out of main thread
                characterDao.getCharacterById(id)
            }
            if (localCharacterEntity != null) {
                return Resource.Success(characterEntityToModel(localCharacterEntity))
            }

            // Unreacheable under current conditions.
            val characterFromApi = apiService.getCharacterById(id)
            withContext(Dispatchers.IO) { // Subprocess out of main thread
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