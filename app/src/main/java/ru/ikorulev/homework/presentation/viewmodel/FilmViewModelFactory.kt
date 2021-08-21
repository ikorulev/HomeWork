package ru.ikorulev.homework.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ikorulev.homework.data.repository.FilmRepository
import ru.ikorulev.homework.data.tmdb.TMDbService
import javax.inject.Inject

class FilmViewModelFactory @Inject constructor(
    val application: Application,
    val repository: FilmRepository,
    val tMDbService: TMDbService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmViewModel::class.java)) {
            return FilmViewModel(application, repository, tMDbService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}