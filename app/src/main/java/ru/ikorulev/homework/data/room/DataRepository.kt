package ru.ikorulev.homework.data.room

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ikorulev.homework.App
import ru.ikorulev.homework.data.FilmItem

class DataRepository() {

    val filmDao = Db.getInstance(App.instance.applicationContext)?.getFilmDao()
    val favouritesDao = Db.getInstance(App.instance.applicationContext)?.getFavouritesDao()
    val watchLaterDao = Db.getInstance(App.instance.applicationContext)?.getWatchLaterDao()

    //Films
    suspend fun getFilms() = withContext(Dispatchers.IO) {
        return@withContext filmDao?.getAll()
    }

    suspend fun updateFilmIsWatchLater(filmItem: FilmItem) = withContext(Dispatchers.IO){
        val item = filmDao?.findByTitle(filmItem.filmTitle)
        if (item!=null) {
            item.isWatchLater = filmItem.isWatchLater
            filmDao?.update(item)
        }
    }

    suspend fun getFavourites() = withContext(Dispatchers.IO) {
        return@withContext favouritesDao?.getAll()
    }



    //WatchLater
    suspend fun getWatchLater() = withContext(Dispatchers.IO) {
        return@withContext watchLaterDao?.getAll()
    }

    suspend fun findWatchLater(filmItem: FilmItem): Boolean = withContext(Dispatchers.IO) {
        return@withContext watchLaterDao?.findByTitle(filmItem.filmTitle)!= null
    }

    suspend fun insertWatchLater(filmItem: FilmItem) = withContext(Dispatchers.IO) {
        watchLaterDao?.insert(
            WatchLaterDb(
                filmItem.filmId,
                filmItem.filmTitle,
                filmItem.filmPath,
                filmItem.filmDetails,
                filmItem.watchDate
            )
        )
    }

    suspend fun deleteWatchLater(filmItem: FilmItem) = withContext(Dispatchers.IO){
        val item = watchLaterDao?.findByTitle(filmItem.filmTitle)
        if (item!=null) {
            watchLaterDao?.delete(item)
        }
    }

}



