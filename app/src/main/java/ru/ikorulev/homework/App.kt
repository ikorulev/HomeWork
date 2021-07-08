package ru.ikorulev.homework

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ikorulev.homework.data.room.Db
import ru.ikorulev.homework.data.tmdb.TMDbService
import ru.ikorulev.homework.domain.Interactor

class App : Application() {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"

        lateinit var instance: App
            private set
    }

    lateinit var tmDbService: TMDbService
    lateinit var interactor: Interactor

        override fun onCreate() {
        super.onCreate()

        instance = this

        initRetrofit()
        initInteractor()
        initRoom()
    }

    private fun initInteractor() {
        interactor = Interactor(tmDbService)
    }

    private fun initRetrofit() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        tmDbService = retrofit.create(TMDbService::class.java)
    }

    private fun initRoom() {
        Db.getInstance(this)?.getFilmDao()
        Db.getInstance(this)?.getFavouritesDao()
    }
}
