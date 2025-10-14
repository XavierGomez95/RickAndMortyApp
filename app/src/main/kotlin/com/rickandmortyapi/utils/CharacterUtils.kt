package com.rickandmortyapi.utils

import com.rickandmortyapi.data.database.entities.CharacterEntity
import com.rickandmortyapi.data.model.CharacterModel


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
