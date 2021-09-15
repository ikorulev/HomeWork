package ru.ikorulev.homework.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.ikorulev.homework.data.room.*

@Database(
    entities = [FilmDb::class, FavouritesDb::class, WatchLaterDb::class],
    version = FilmDatabase.VERSION
)

@TypeConverters(DateConverters::class)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun getFilmDao(): FilmDao
    abstract fun getFavouritesDao(): FavouritesDao
    abstract fun getWatchLaterDao(): WatchLaterDao

    companion object {
        const val VERSION = 10
    }
}