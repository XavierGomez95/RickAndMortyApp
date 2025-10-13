package com.rickandmortyapi.di

import com.rickandmortyapi.data.repository.CharacterRepository
import com.rickandmortyapi.data.repository.EpisodeRepository
import com.rickandmortyapi.data.repository.interfaces.CharacterRepositoryInterface
import com.rickandmortyapi.data.repository.interfaces.EpisodeRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        characterRepository: CharacterRepository
    ): CharacterRepositoryInterface

    @Binds
    @Singleton
    abstract fun bindEpisodeRepository(
        episodeRepository: EpisodeRepository
    ): EpisodeRepositoryInterface
}