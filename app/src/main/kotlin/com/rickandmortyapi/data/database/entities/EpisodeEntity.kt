package com.rickandmortyapi.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode_table")
data class EpisodeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    //val characters: List<String>,
    val url: String,
    val created: String
)