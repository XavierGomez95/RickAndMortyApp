package com.rickandmortyapi.data.model

import kotlinx.serialization.Serializable


@Serializable
data class LocationModel (
    val id: Int,
    val name: String = "",
    val type: String = "",
    val dimension: String = "",
    val residents: List<String> = emptyList(),
    val url: String = "",
    val created: String = "",
)