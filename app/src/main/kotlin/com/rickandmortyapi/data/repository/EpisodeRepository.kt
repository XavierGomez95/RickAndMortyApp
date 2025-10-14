package com.rickandmortyapi.data.repository

import android.content.Context
import com.rickandmortyapi.data.database.dao.EpisodeDao
import com.rickandmortyapi.data.model.EpisodeModel
import com.rickandmortyapi.data.repository.interfaces.EpisodeRepositoryInterface
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource
import com.rickandmortyapi.utils.Constants
import com.rickandmortyapi.utils.episodeEntityToModel
import com.rickandmortyapi.utils.episodeModelToEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import androidx.core.content.edit


class EpisodeRepository @Inject constructor (
    private val apiService: RickAndMortyApiService,
    private val episodeDao: EpisodeDao,
    @ApplicationContext private val context: Context
) : EpisodeRepositoryInterface {
    override suspend fun retrieveAllEpisodes(): Resource<List<EpisodeModel>> {
        try {
            val sharedPreferences = context
                .getSharedPreferences("episode_sync_preferences", Context.MODE_PRIVATE)
            val lastUpdateTime = sharedPreferences.getLong("last_update_time", 0L)
            val currentTime = System.currentTimeMillis()

            val localEpisodesData = withContext(Dispatchers.IO) { // Subprocess out of main thread
                episodeDao.getEpisodes()
            }

            if (currentTime - lastUpdateTime > Constants.DAY_TIME_MILLIS ||
                localEpisodesData.isEmpty()) {

                val allEpisodeData = mutableListOf<EpisodeModel>()
                val firstPage = 1
                val firstEpisodeBatch = apiService.getEpisodeBatch(firstPage)
                allEpisodeData.addAll(firstEpisodeBatch.results)

                val totalPages = firstEpisodeBatch.info.pages
                for (currentPage in 2..totalPages) {
                    val episodesBatch = apiService.getEpisodeBatch(currentPage)
                    allEpisodeData.addAll(episodesBatch.results)
                }

                withContext(Dispatchers.IO) { // Subprocess out of main thread
                    episodeDao.clearAllEpisodes()
                    episodeDao.insertEpisodes(allEpisodeData.map {
                        episodeModelToEntity(it)
                    })
                }

                sharedPreferences.edit { putLong("last_update_time", currentTime) }
                Resource.Success(allEpisodeData)

                return Resource.Success(allEpisodeData)
            } else {
                val localModels = localEpisodesData.map { episodeEntityToModel(it) }
                return Resource.Success(localModels)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Failure
        }
    }

    override suspend fun getEpisodeById(id: Int): Resource<EpisodeModel> {
        try {
            val localEpisodeEntity = withContext(Dispatchers.IO) { // Subprocess out of main thread
                episodeDao.getEpisodeById(id)
            }
            if (localEpisodeEntity != null) {
                return Resource.Success(episodeEntityToModel(localEpisodeEntity))
            }

            // Unreacheable under current conditions.
            val episodeFromApi = apiService.getEpisodeById(id)
            withContext(Dispatchers.IO) { // Subprocess out of main thread
                episodeDao.insertEpisode(episodeModelToEntity(episodeFromApi))
            }
            return Resource.Success(episodeFromApi)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Failure
        }
    }
}