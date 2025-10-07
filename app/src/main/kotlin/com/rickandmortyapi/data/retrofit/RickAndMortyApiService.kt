package com.rickandmortyapi.data.retrofit


import com.rickandmortyapi.data.model.Character
import com.rickandmortyapi.data.model.Episode
import com.rickandmortyapi.data.model.Location
import com.rickandmortyapi.data.model.repository.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// https://square.github.io/retrofit/declarations/#url-manipulation
interface RickAndMortyApiService {
    @GET("character") // Relative path
    suspend fun getCharacterBatch(@Query("page") page: Int):
            ApiResponse<Character>

    @GET("character") // Relative path
    suspend fun getCharacterById(@Path("id") id: Int):
            List<Character>

    @GET("episode") // Relative path
    suspend fun getEpisodeBatch(@Query("page") page: Int):
            ApiResponse<Episode>

    @GET("location") // Relative path
    suspend fun getLocationBatch(@Query("page") page: Int):
            ApiResponse<Location>
}