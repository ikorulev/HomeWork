package ru.ikorulev.homework.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.ikorulev.homework.BuildConfig
import ru.ikorulev.homework.data.tmdb.TMDbService
import java.util.concurrent.TimeUnit

@Module
class NetModule {

    /*
    Header Initialization
    */

    @Provides
    protected fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    protected fun provideOkHttpClientDefault(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)

        return okBuilder.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TMDbService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideNetworkService(retrofit: Retrofit): TMDbService {
        return retrofit.create(TMDbService::class.java)
    }
}