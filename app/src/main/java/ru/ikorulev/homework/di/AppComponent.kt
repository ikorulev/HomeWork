package ru.ikorulev.homework.di

import android.app.Application
import dagger.Component
import ru.ikorulev.homework.data.repository.FilmDatabase
import ru.ikorulev.homework.data.repository.FilmRepository
import ru.ikorulev.homework.data.room.FavouritesDao
import ru.ikorulev.homework.data.room.FilmDao
import ru.ikorulev.homework.data.room.WatchLaterDao
import ru.ikorulev.homework.presentation.view.FilmListFragment
import ru.ikorulev.homework.presentation.view.MainActivity
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModel
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [AppModule::class, RoomModule::class, NetModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun filmViewModel(): FilmViewModel
    fun filmViewModelFactory(): FilmViewModelFactory
    fun filmDao(): FilmDao
    fun favouritesDao(): FavouritesDao
    fun watchLaterDao(): WatchLaterDao
    fun filmDatabase(): FilmDatabase
    fun filmRepository(): FilmRepository
    fun application(): Application
}