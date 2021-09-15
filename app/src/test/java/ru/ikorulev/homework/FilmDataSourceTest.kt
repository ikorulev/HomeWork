package ru.ikorulev.homework

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import ru.ikorulev.homework.data.repository.FilmDataSource
import ru.ikorulev.homework.data.room.FavouritesDao
import ru.ikorulev.homework.data.room.FilmDao
import ru.ikorulev.homework.data.room.WatchLaterDao

class FilmDataSourceTest {

    private lateinit var filmDao: FilmDao
    private lateinit var favouritesDao: FavouritesDao
    private lateinit var watchLaterDao: WatchLaterDao
    private lateinit var filmDataSource: FilmDataSource

    @Before
    fun setup() {
        filmDao = mock()
        favouritesDao = mock()
        watchLaterDao = mock()
        filmDataSource = FilmDataSource(filmDao, favouritesDao, watchLaterDao)
    }

    @Test
    fun clearTables_shouldDeleteAll() {
        filmDataSource.clearTables()

        verify(filmDao).deleteAll()
        verify(favouritesDao).deleteAll()
        verify(watchLaterDao).deleteAll()
    }

    @Test
    fun getFilms_shouldgetAll() {
        filmDataSource.getFilms()

        verify(filmDao).getAll()
    }

}