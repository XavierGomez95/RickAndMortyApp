package com.rickandmortyapi.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Episode (
    val name: String = "",
    val image: String = ""
)