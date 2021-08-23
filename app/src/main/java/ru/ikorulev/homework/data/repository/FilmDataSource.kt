package ru.ikorulev.homework.data.repository

import io.reactivex.Flowable
import io.reactivex.Single
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.room.*
import java.util.*
import javax.inject.Inject

class FilmDataSource @Inject constructor(
    private val filmDao: FilmDao,
    private val favouritesDao : FavouritesDao,
    private val watchLaterDao: WatchLaterDao
)  : FilmRepository {
    override fun clearTables() {
        filmDao.deleteAll()
        favouritesDao.deleteAll()
        watchLaterDao.deleteAll()
    }

    override fun getFilms(): Flowable<List<FilmDb>?> {
        return filmDao.getAll()
    }

    override fun getListFilms(): Single<List<FilmDb>?> {
        return filmDao.getListAll()
    }

    override fun updateFilms(items: List<FilmDb>) {
        filmDao.updateAll(items)
    }

    override fun insertFilms(filmDb: List<FilmDb>) {
        filmDao.insert(filmDb)
    }

    override fun findFilmByTitle(title: String): FilmItem? {
        var item: FilmItem? = null
        val itemDb = filmDao.findByTitle(title)
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
                calendar.time
            )
        }
        return item
    }

    override fun updateFilm(filmItem: FilmItem) {
        val item = filmDao.findById(filmItem.filmId)
        if (item != null) {
            item.isFavorite = filmItem.isFavorite
            item.isWatchLater = filmItem.isWatchLater
            filmDao.update(item)
        }
    }

    override fun getFavourites(): Flowable<List<FavouritesDb>?> {
        return favouritesDao.getAll()
    }

    override fun insertFavourites(filmItem: FilmItem) {
        favouritesDao.insert(
            FavouritesDb(
                filmItem.filmId,
                filmItem.filmTitle,
                filmItem.filmPath
            )
        )
    }

    override fun deleteFavourites(filmItem: FilmItem) {
        val item = favouritesDao.findById(filmItem.filmId)
        if (item != null) {
            favouritesDao.delete(item)
        }
    }

    override fun getWatchLater(): Flowable<List<WatchLaterDb>?> {
        return watchLaterDao.getAll()
    }

    override fun insertWatchLater(filmItem: FilmItem) {
        watchLaterDao.insert(
            WatchLaterDb(
                filmItem.filmId,
                filmItem.filmTitle,
                filmItem.filmPath,
                filmItem.filmDetails,
                filmItem.watchDate
            )
        )
    }

    override fun deleteWatchLater(filmItem: FilmItem) {
        val item = watchLaterDao.findById(filmItem.filmId)
        if (item != null) {
            watchLaterDao.delete(item)
        }
    }
}