package ru.ikorulev.homework.data.room

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ikorulev.homework.App
import ru.ikorulev.homework.data.FilmItem
import java.util.*

class DataRepository {

    private val filmDao = Db.getInstance(App.instance.applicationContext)?.getFilmDao()
    private val favouritesDao = Db.getInstance(App.instance.applicationContext)?.getFavouritesDao()
    private val watchLaterDao = Db.getInstance(App.instance.applicationContext)?.getWatchLaterDao()

    suspend fun clearTables() = withContext(Dispatchers.IO) {
        filmDao?.deleteAll()
        favouritesDao?.deleteAll()
        watchLaterDao?.deleteAll()
    }

    //Films
    suspend fun getFilms() = withContext(Dispatchers.IO) {
        return@withContext filmDao?.getAll()
    }

    suspend fun isEmpty() = withContext(Dispatchers.IO) {
        return@withContext filmDao?.getListAll()?.size == 0
    }

    suspend fun insertFilmDb(filmDb: List<FilmDb>) = withContext(Dispatchers.IO) {
        filmDao?.insert(filmDb)
    }

    suspend fun findFilm(title: String): FilmItem? = withContext(Dispatchers.IO) {
        var item:FilmItem?=null
        val itemDb = filmDao?.findByTitle(title)
        if (itemDb != null) {
            val calendar = Calendar.getInstance()
            item = FilmItem(
                itemDb.filmId,
                itemDb.filmTitle,
                itemDb.filmPath,
                itemDb.filmDetails,
                itemDb.isSelected,
                itemDb.isFavorite,
                itemDb.isWatchLater,
                calendar.time)
        }
        return@withContext item
    }


    fun updateFilmIsSelected(filmItem: FilmItem) {
        val items = filmDao?.getListAll()
        if (items != null) {
            items.forEach {
                it.isSelected = it.filmTitle == filmItem.filmTitle
            }
            filmDao?.updateAll(items.toList())
        }
    }

    suspend fun updateFilmIsFavorite(filmItem: FilmItem) = withContext(Dispatchers.IO){
        val item = filmDao?.findByTitle(filmItem.filmTitle)
        if (item!=null) {
            item.isFavorite = filmItem.isFavorite
            filmDao?.update(item)
        }
    }

    suspend fun updateFilmIsWatchLater(filmItem: FilmItem) = withContext(Dispatchers.IO){
        val item = filmDao?.findByTitle(filmItem.filmTitle)
        if (item!=null) {
            item.isWatchLater = filmItem.isWatchLater
            filmDao?.update(item)
        }
    }

    //Favourites
    suspend fun getFavourites() = withContext(Dispatchers.IO) {
        return@withContext favouritesDao?.getAll()
    }

    suspend fun insertFavourites(filmItem: FilmItem) = withContext(Dispatchers.IO) {
        favouritesDao?.insert(
            FavouritesDb(
                filmItem.filmId,
                filmItem.filmTitle,
                filmItem.filmPath
            )
        )
    }

    suspend fun deleteFavourites(filmItem: FilmItem) = withContext(Dispatchers.IO){
        val item = favouritesDao?.findByTitle(filmItem.filmTitle)
        if (item!=null) {
            favouritesDao?.delete(item)
        }
    }

    //WatchLater
    suspend fun getWatchLater() = withContext(Dispatchers.IO) {
        return@withContext watchLaterDao?.getAll()
    }

    suspend fun getListWatchLater() = withContext(Dispatchers.IO) {
        return@withContext watchLaterDao?.getListAll()
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



