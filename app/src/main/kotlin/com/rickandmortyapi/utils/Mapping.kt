package com.rickandmortyapi.utils

import com.rickandmortyapi.data.database.entities.CharacterEntity
import com.rickandmortyapi.data.database.entities.EpisodeEntity
import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.model.EpisodeModel

fun characterEntityToModel(entity: CharacterEntity): CharacterModel {
    return CharacterModel(
        id = entity.id,
        name = entity.name,
        status = entity.status,
        species = entity.species,
        image = entity.image,
        gender = entity.gender
    )
}


fun characterModelToEntity(model: CharacterModel): CharacterEntity {
    return CharacterEntity(
        id = model.id,
        name = model.name,
        status = model.status,
        species = model.species,
        image = model.image,
        gender = model.gender
    )
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