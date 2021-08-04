package ru.ikorulev.homework.data.room

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ikorulev.homework.App

class DataRepository() {

    val filmDao = Db.getInstance(App.instance.applicationContext)?.getFilmDao()
    val favouritesDao = Db.getInstance(App.instance.applicationContext)?.getFavouritesDao()

    suspend fun getFilms() = withContext(Dispatchers.IO) {
        return@withContext filmDao?.getAll()
    }

    suspend fun getFavourites() = withContext(Dispatchers.IO) {
        return@withContext favouritesDao?.getAll()
    }
}



