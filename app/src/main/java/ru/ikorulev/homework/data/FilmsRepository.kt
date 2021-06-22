package ru.ikorulev.homework.data

import java.util.ArrayList

class FilmsRepository {

    val cachedFilms = ArrayList<FilmItem>()


    fun addToCache(films: List<FilmItem>) {
        this.cachedFilms.addAll(films)
    }


}