package com.rickandmortyapi.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val status: String,
    val gender: String,
    val species: String,
)
