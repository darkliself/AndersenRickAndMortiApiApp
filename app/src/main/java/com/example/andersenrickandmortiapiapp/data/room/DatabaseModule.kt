package com.example.andersenrickandmortiapiapp.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ApplicationRoomDB {
        return Room.databaseBuilder(
            appContext,
            ApplicationRoomDB::class.java,
            "RickAndMortyDB"
        ).build()
    }

    @InstallIn(SingletonComponent::class)
    @Module
    class DatabaseModule {
        @Provides
        fun provideEpisodeDao(appDatabase: ApplicationRoomDB): EpisodeDao {
            return appDatabase.episodeDao
        }
        @Provides
        fun provideLocationDao(appDatabase: ApplicationRoomDB): LocationDao {
            return appDatabase.locationDao
        }
    }

}