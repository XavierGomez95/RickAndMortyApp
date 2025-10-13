package com.rickandmortyapi.data.model

import kotlinx.serialization.Serializable

@Serializable
data class OriginModel(
    val name: String = "",
    val url: String = ""
)
