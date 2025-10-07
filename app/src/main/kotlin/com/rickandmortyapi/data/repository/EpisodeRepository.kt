package com.rickandmortyapi.data.repository

import com.rickandmortyapi.data.model.Episode
import com.rickandmortyapi.data.repository.interfaces.EpisodeRepositoryInterface
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource

class EpisodeRepository (
    private val apiService: RickAndMortyApiService
) : EpisodeRepositoryInterface {
    override suspend fun retrieveAllEpisodes(): Resource<List<Episode>> {
        try {
            val allEpisodes = mutableListOf<Episode>()
            val firstPage = 1

            val firstEpisodeBatch = apiService.getEpisodeBatch(firstPage)
            allEpisodes.addAll(firstEpisodeBatch.results)

            val totalPages = firstEpisodeBatch.info.pages
            for (currentPage in 2 .. totalPages) {
                val episodesBatch = apiService.getEpisodeBatch(currentPage)
                allEpisodes.addAll(episodesBatch.results)
            }

            return Resource.Success(allEpisodes)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Failure
        }
    }
}