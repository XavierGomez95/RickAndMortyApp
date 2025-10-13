package com.rickandmortyapi.data.repository

import android.util.Log
import com.rickandmortyapi.data.database.dao.EpisodeDao
import com.rickandmortyapi.data.database.entities.EpisodeEntity
import com.rickandmortyapi.data.model.EpisodeModel
import com.rickandmortyapi.data.repository.interfaces.EpisodeRepositoryInterface
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class EpisodeRepository @Inject constructor (
    private val apiService: RickAndMortyApiService,
    private val episodeDao: EpisodeDao
) : EpisodeRepositoryInterface {
    override suspend fun retrieveAllEpisodes(): Resource<List<EpisodeModel>> {
        try {
            val allEpisodeData = mutableListOf<EpisodeModel>()
            val firstPage = 1

            val firstEpisodeBatch = apiService.getEpisodeBatch(firstPage)
            allEpisodeData.addAll(firstEpisodeBatch.results)

            val totalPages = firstEpisodeBatch.info.pages
            for (currentPage in 2 .. totalPages) {
                val episodesBatch = apiService.getEpisodeBatch(currentPage)
                allEpisodeData.addAll(episodesBatch.results)
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
                val localEpisode = EpisodeModel(
                    id = localEpisodeEntity.id,
                    name = localEpisodeEntity.name,
                    airDate = localEpisodeEntity.airDate,
                    episode = localEpisodeEntity.episode,
                    url = localEpisodeEntity.url,
                    created = localEpisodeEntity.created,
                    //characters = localEpisodeEntity.characters
                )
                return Resource.Success(localEpisode)
            }

            val episodeFromApi = apiService.getEpisodeById(id)
            withContext(Dispatchers.IO) {
                episodeDao.insertEpisode(
                    episode = EpisodeEntity(
                        id = episodeFromApi.id,
                        name = episodeFromApi.name,
                        airDate = episodeFromApi.airDate,
                        episode = episodeFromApi.episode,
                        url = episodeFromApi.url,
                        created = episodeFromApi.created,
                        //characters = episodeFromApi.characters
                    )
                )
            }
            return Resource.Success(episodeFromApi)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ÇÇÇÇ__EpisodeRepository", "Error getting episode by id", e)
            return Resource.Failure
        }
    }
}