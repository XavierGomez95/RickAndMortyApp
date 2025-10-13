package com.rickandmortyapi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rickandmortyapi.data.database.dao.CharacterDao
import com.rickandmortyapi.data.database.dao.EpisodeDao
import com.rickandmortyapi.data.database.entities.CharacterEntity
import com.rickandmortyapi.data.database.entities.EpisodeEntity

@Database(entities = [EpisodeEntity::class, CharacterEntity::class], version = 2, exportSchema = false) // To not store data from other versions (exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao(): EpisodeDao
}