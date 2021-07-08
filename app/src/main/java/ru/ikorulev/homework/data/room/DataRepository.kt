package ru.ikorulev.homework.data.room

import androidx.lifecycle.LiveData

class DataRepository(filmDao: FilmDao?, favouritesDao: FavouritesDao?){

    val films: LiveData<List<FilmDb>>? = filmDao?.getAll()
    val favourites: LiveData<List<FavouritesDb>>? = favouritesDao?.getAll()

}

