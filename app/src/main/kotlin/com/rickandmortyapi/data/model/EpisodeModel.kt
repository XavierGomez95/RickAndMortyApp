package com.rickandmortyapi.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class EpisodeModel (
    val id: Int,
    val name: String = "",
    @SerializedName("air_date")
    val airDate: String = "",
    val episode: String = "",
    val characters: List<String> = emptyList(),
    val url: String = "",
    val created: String = "",

    // Campos para la UI
    val uiDate: String? = null,
    val uiSeason: String? = null,
    val uiEpisode: String? = null
)