package ru.ikorulev.homework

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ikorulev.homework.data.room.DataRepository
import ru.ikorulev.homework.data.room.Db
import ru.ikorulev.homework.data.tmdb.TMDbService
import ru.ikorulev.homework.domain.Interactor
import java.util.concurrent.TimeUnit

class App : Application() {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"

        lateinit var instance: App
            private set
    }

    lateinit var tmDbService: TMDbService
    lateinit var dataRepository: DataRepository
    lateinit var interactor: Interactor

        override fun onCreate() {
        super.onCreate()

        instance = this

        initRetrofit()
        initInteractor()
        //initRoom()
    }

    private fun initInteractor() {
        dataRepository = DataRepository()
        interactor = Interactor(tmDbService, dataRepository)
    }

    private fun initRetrofit() {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        tmDbService = retrofit.create(TMDbService::class.java)
    }

    private fun initRoom() {
        Db.getInstance(this)?.getFilmDao()
        Db.getInstance(this)?.getFavouritesDao()
    }
}
