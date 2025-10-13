package com.rickandmortyapi.data.repository.interfaces

import com.rickandmortyapi.data.model.EpisodeModel
import com.rickandmortyapi.data.utils.Resource

interface EpisodeRepositoryInterface {
    suspend fun retrieveAllEpisodes(): Resource<List<EpisodeModel>>
}