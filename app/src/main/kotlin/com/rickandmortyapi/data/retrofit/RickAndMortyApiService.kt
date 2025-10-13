package com.rickandmortyapi.data.retrofit


import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.model.EpisodeModel
import com.rickandmortyapi.data.model.LocationModel
import com.rickandmortyapi.data.model.repository.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// https://square.github.io/retrofit/declarations/#url-manipulation
interface RickAndMortyApiService {
    @GET("character") // Relative path
    suspend fun getCharacterBatch(@Query("page") page: Int):
            ApiResponse<CharacterModel>

    @GET("character/{id}") // Relative path
    suspend fun getCharacterById(@Path("id") id: Int):
            CharacterModel

    @GET("character/{ids}")
    suspend fun getCharactersByIds(@Path("ids") ids: String):
            List<CharacterModel>

    @GET("episode") // Relative path
    suspend fun getEpisodeBatch(@Query("page") page: Int):
            ApiResponse<EpisodeModel>

    @GET("location") // Relative path
    suspend fun getLocationBatch(@Query("page") page: Int):
            ApiResponse<LocationModel>
}