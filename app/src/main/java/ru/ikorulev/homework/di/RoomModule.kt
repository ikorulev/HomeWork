package ru.ikorulev.homework.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.ikorulev.homework.data.room.*
import ru.ikorulev.homework.data.repository.FilmDataSource
import ru.ikorulev.homework.data.repository.FilmDatabase
import ru.ikorulev.homework.data.repository.FilmRepository
import javax.inject.Singleton

@Module
class RoomModule(mApplication: Application) {
    private val filmDatabase: FilmDatabase = Room
        .databaseBuilder(mApplication, FilmDatabase::class.java, "films.db")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): FilmDatabase {
        return filmDatabase
    }

    @Singleton
    @Provides
    fun providesFilmDao(filmDatabase: FilmDatabase): FilmDao {
        return filmDatabase.getFilmDao()
    }

    @Singleton
    @Provides
    fun providesFavouritesDao(filmDatabase: FilmDatabase): FavouritesDao {
        return filmDatabase.getFavouritesDao()
    }

    @Singleton
    @Provides
    fun providesWatchLaterDao(filmDatabase: FilmDatabase): WatchLaterDao {
        return filmDatabase.getWatchLaterDao()
    }

    @Singleton
    @Provides
    fun filmRepository(filmDao : FilmDao, favouritesDao : FavouritesDao, watchLaterDao: WatchLaterDao): FilmRepository {
        return FilmDataSource(filmDao, favouritesDao, watchLaterDao)
    }

}