package com.rickandmortyapi.utils

fun convertAirDateForUi(airDate: String): String {
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