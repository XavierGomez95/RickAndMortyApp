package com.rickandmortyapi.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Character (
    val name: String = "",
    val image: String = "",
    val status: String = "",
    val gender: String = "",
    val species: String = ""
)