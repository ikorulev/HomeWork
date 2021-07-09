package ru.ikorulev.homework.data.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataRepository(filmDao: FilmDao?, favouritesDao: FavouritesDao?){

    val films: LiveData<List<FilmDb>>? = filmDao?.getAll()
    val favourites: LiveData<List<FavouritesDb>>? = favouritesDao?.getAll()
    val error: LiveData<String> = MutableLiveData()

}

