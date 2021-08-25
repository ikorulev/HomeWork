package ru.ikorulev.homework

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Flowable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.toFlowable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.manipulation.Ordering
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.repository.FilmRepository
import ru.ikorulev.homework.data.room.FilmDb
import ru.ikorulev.homework.data.tmdb.TMDbService
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModel
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModelFactory


class FilmDbTest {

    private lateinit var filmDb: FilmDb

    @Before
    fun setUp() {
        filmDb = FilmDb(
            1,
            "Title",
            "Path",
            "Details",
            1
        )
    }

    @Test
    fun whenCreatingFilmDb_shouldNotIsSelected() {
        Assert.assertFalse(filmDb.isSelected)
    }

    @Test
    fun whenCreatingFilmDb_shouldNotIsFavorite() {
        Assert.assertFalse(filmDb.isFavorite)
    }

    @Test
    fun whenCreatingFilmDb_shouldNotIsWatchLater() {
        Assert.assertFalse(filmDb.isWatchLater)
    }

}


