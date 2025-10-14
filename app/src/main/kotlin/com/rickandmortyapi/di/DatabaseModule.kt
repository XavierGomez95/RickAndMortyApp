package com.rickandmortyapi.di

import android.content.Context
import androidx.room.Room
import com.rickandmortyapi.data.database.AppDatabase
import com.rickandmortyapi.data.database.dao.CharacterDao
import com.rickandmortyapi.data.database.dao.EpisodeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "app_database"
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = AppDatabase::class.java,
            name = DATABASE_NAME
        ).fallbackToDestructiveMigration(true) // function without argument is deprecated
            .build()
    }

    @Provides
    fun provideCharacterDao(appDatabase: AppDatabase): CharacterDao {
        return appDatabase.characterDao()
    }

    @Provides
    fun provideEpisodeDao(appDatabase: AppDatabase): EpisodeDao {
        return appDatabase.episodeDao()
    }
}