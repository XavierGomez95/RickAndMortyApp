package com.rickandmortyapi.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Location (
    val name: String = "",
    val image: String = ""
)