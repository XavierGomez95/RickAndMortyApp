package com.rickandmortyapi.utils

import com.rickandmortyapi.data.database.entities.EpisodeEntity
import com.rickandmortyapi.data.model.EpisodeModel

fun convertAirDateForUi(airDate: String): String {
    if (airDate.isBlank()) return "Unknown"
    return airDate.split("T").getOrNull(0) ?: "Unknown"
}

fun convertSeasonAndEpisodeForUi(code: String): Map<String, String> {
    val parts = code.replace("E", " E").split(" ")

    val rawSeason = parts.getOrNull(0)?.removePrefix("S")?.trim()
    val rawEpisode = parts.getOrNull(1)?.removePrefix("E")?.trim()

    val uiSeason = if (rawSeason != null && rawSeason.isNotEmpty()) rawSeason else "?"
    val uiEpisode = if (rawEpisode != null && rawEpisode.isNotEmpty()) rawEpisode else "?"

    val partsMap = mutableMapOf<String, String>()

    partsMap["season"] = uiSeason
    partsMap["episode"] = uiEpisode

    return partsMap
}

fun episodeEntityToModel(entity: EpisodeEntity): EpisodeModel {
    return EpisodeModel(
        id = entity.id,
        name = entity.name,
        airDate = entity.airDate,
        episode = entity.episode,
    )
}


fun episodeModelToEntity(model: EpisodeModel): EpisodeEntity {
    return EpisodeEntity(
        id = model.id,
        name = model.name,
        airDate = model.airDate,
        episode = model.episode,
        url = model.url,
        created = model.created,
    )
}