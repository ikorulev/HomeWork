package ru.ikorulev.homework

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Flowable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.ikorulev.homework.data.FilmItem
import ru.ikorulev.homework.data.repository.FilmRepository
import ru.ikorulev.homework.data.room.FilmDb
import ru.ikorulev.homework.data.tmdb.TMDbService
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModel
import java.util.*

class FilmViewModelTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    lateinit var repository: FilmRepository
    lateinit var tMDbService: TMDbService

    @Before
    fun setUp() {
        repository = mock()
        tMDbService = mock()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun initFilms() {
        val items = listOf(FilmDb(1, "", "", "", 1))

        whenever(repository.getFilms())
            .thenReturn(Flowable.just(items))

        val filmViewModel = FilmViewModel(mock(), repository, tMDbService)
        filmViewModel.initFilms()
        assertEquals(items[0].filmId, filmViewModel.films.value?.get(0)?.filmId)
    }

    @Test
    fun loadFilms() {
        val filmViewModel = FilmViewModel(mock(), repository, tMDbService)
        filmViewModel.loadFilms(1)

        verify(tMDbService).getPopularFilms()
    }

    @Test
    fun insertFavourites() {
        val calendar = Calendar.getInstance()
        val filmItem = FilmItem(1, "", "", "", false, false, false, calendar.time)

        val filmViewModel = FilmViewModel(mock(), repository, tMDbService)

        filmViewModel.insertFavourites(filmItem)

        verify(repository).insertFavourites(filmItem)
    }
}
