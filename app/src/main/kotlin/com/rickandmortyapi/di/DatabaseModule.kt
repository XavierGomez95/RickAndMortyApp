package com.rickandmortyapi.di

import android.content.Context
import androidx.room.Room
import com.rickandmortyapi.data.database.AppDatabase
import com.rickandmortyapi.data.database.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = AppDatabase::class.java,
            name = "app_database"
        ).build()
    }

    @Provides
    fun provideCharacterDao(appDatabase: AppDatabase): CharacterDao {
        return appDatabase.characterDao()
    }
}