package ru.ikorulev.homework.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FilmDb::class, FavouritesDb::class), version = 4)
abstract class AppDb : RoomDatabase() {
    abstract fun getFilmDao(): FilmDao
    abstract fun getFavouritesDao(): FavouritesDao
}