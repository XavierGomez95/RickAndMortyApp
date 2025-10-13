package com.rickandmortyapi.data.repository.interfaces

import com.rickandmortyapi.data.model.LocationModel
import com.rickandmortyapi.data.utils.Resource

interface LocationRepositoryInterface {
    suspend fun retrieveAllLocations(): Resource<List<LocationModel>>
}