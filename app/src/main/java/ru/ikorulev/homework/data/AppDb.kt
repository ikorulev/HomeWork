package ru.ikorulev.homework.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(FilmDb::class, FavouritesDb::class), version = 2)
abstract class AppDb : RoomDatabase() {
    abstract fun getFilmDao(): FilmDao
    abstract fun getFavouritesDao(): FavouritesDao
}