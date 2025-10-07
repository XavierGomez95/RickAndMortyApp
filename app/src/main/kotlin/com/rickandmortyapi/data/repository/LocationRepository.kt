package com.rickandmortyapi.data.repository

import com.rickandmortyapi.data.model.Location
import com.rickandmortyapi.data.repository.interfaces.LocationRepositoryInterface
import com.rickandmortyapi.data.retrofit.RickAndMortyApiService
import com.rickandmortyapi.data.utils.Resource

class LocationRepository (
    private val apiService: RickAndMortyApiService
) : LocationRepositoryInterface {
    override suspend fun retrieveAllLocations(): Resource<List<Location>> {
        try {
            val allLocations = mutableListOf<Location>()
            val firstPage = 1

            val firstLocationBatch = apiService.getLocationBatch(firstPage)
            allLocations.addAll(firstLocationBatch.results)

            val totalPages = firstLocationBatch.info.pages
            for (currentPage in 2 .. totalPages) {
                val locationsBatch = apiService.getLocationBatch(currentPage)
                allLocations.addAll(locationsBatch.results)
            }

            return Resource.Success(allLocations)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Failure
        }
    }
}