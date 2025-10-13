package com.rickandmortyapi.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rickandmortyapi.data.database.entities.EpisodeEntity

@Dao
interface EpisodeDao {
    @Query("SELECT * FROM episode_table")
    fun getEpisodes(): List<EpisodeEntity>

    @Query("SELECT * FROM episode_table WHERE id = :id")
    fun getEpisodeById(id: Int): EpisodeEntity?

    @Insert
    suspend fun insertEpisodes(episode: List<EpisodeEntity>)

    @Insert
    suspend fun insertEpisode(episode: EpisodeEntity)

    @Query("DELETE FROM episode_table")
    suspend fun clearAll()
}