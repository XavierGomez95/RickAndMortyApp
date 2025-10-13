package com.rickandmortyapi.data.repository

import com.rickandmortyapi.data.model.EpisodeModel
import com.rickandmortyapi.data.repository.interfaces.EpisodeRepositoryInterface
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource

class EpisodeRepository (
    private val apiService: RickAndMortyApiService
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
}