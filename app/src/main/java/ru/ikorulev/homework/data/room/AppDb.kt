package ru.ikorulev.homework.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(
    FilmDb::class,
    FavouritesDb::class,
    WatchLaterDb::class), version = 7)

@TypeConverters(DateConverters::class)
abstract class AppDb : RoomDatabase() {
    abstract fun getFilmDao(): FilmDao
    abstract fun getFavouritesDao(): FavouritesDao
    abstract fun getWatchLaterDao(): WatchLaterDao
}