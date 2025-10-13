package com.rickandmortyapi.data.model

import kotlinx.serialization.Serializable


@Serializable
data class CharacterModel (
    val id: Int,
    val name: String = "",
    val image: String = "",
    val status: String = "",
    val gender: String = "",
    val species: String = "",
)