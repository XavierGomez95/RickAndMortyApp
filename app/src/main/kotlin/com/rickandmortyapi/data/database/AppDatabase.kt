package com.rickandmortyapi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rickandmortyapi.data.database.dao.CharacterDao
import com.rickandmortyapi.data.database.entities.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false) // To not store data from other versions (exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}