package com.rickandmortyapi.data.repository

import android.content.Context
import android.util.Log
import com.rickandmortyapi.data.database.dao.EpisodeDao
import com.rickandmortyapi.data.model.EpisodeModel
import com.rickandmortyapi.data.repository.interfaces.EpisodeRepositoryInterface
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource
import com.rickandmortyapi.utils.episodeEntityToModel
import com.rickandmortyapi.utils.episodeModelToEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class EpisodeRepository @Inject constructor (
    private val apiService: RickAndMortyApiService,
    private val episodeDao: EpisodeDao
) : EpisodeRepositoryInterface {
    override suspend fun retrieveAllEpisodes(): Resource<List<EpisodeModel>> {
        try {
            val localEpisodes = withContext(Dispatchers.IO) {
                episodeDao.getEpisodes()
            }
            if (localEpisodes.isNotEmpty()) {
                val localModels = localEpisodes.map { episodeEntityToModel(it) }
                return Resource.Success(localModels)
            }

            val allEpisodeData = mutableListOf<EpisodeModel>()
            val firstPage = 1
            val firstEpisodeBatch = apiService.getEpisodeBatch(firstPage)
            allEpisodeData.addAll(firstEpisodeBatch.results)

            val totalPages = firstEpisodeBatch.info.pages
            for (currentPage in 2..totalPages) {
                val episodesBatch = apiService.getEpisodeBatch(currentPage)
                allEpisodeData.addAll(episodesBatch.results)
            }

            withContext(Dispatchers.IO) {
                episodeDao.insertEpisodes(allEpisodeData.map { episodeModelToEntity(it) })
            }

            return Resource.Success(allEpisodeData)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Failure
        }
    }

    override suspend fun getEpisodeById(id: Int): Resource<EpisodeModel> {
        try {
            val localEpisodeEntity = withContext(Dispatchers.IO) {
                episodeDao.getEpisodeById(id)
            }
            if (localEpisodeEntity != null) {
                return Resource.Success(episodeEntityToModel(localEpisodeEntity))
            }

            val episodeFromApi = apiService.getEpisodeById(id)
            withContext(Dispatchers.IO) {
                episodeDao.insertEpisode(episodeModelToEntity(episodeFromApi))
            }
            return Resource.Success(episodeFromApi)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ÇÇÇÇ__EpisodeRepository", "Error getting episode by id", e) // TODO: DELETE
            return Resource.Failure
        }
    }
}