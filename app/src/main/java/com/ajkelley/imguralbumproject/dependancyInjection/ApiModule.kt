package com.ajkelley.imguralbumproject.dependancyInjection

import com.ajkelley.imguralbumproject.App
import com.ajkelley.imguralbumproject.BuildConfig
import com.ajkelley.imguralbumproject.repositories.ImgurRepository
import com.ajkelley.imguralbumproject.web.Urls
import com.ajkelley.imguralbumproject.web.services.ImgurServices
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule(var application: App) {
    private val CACHE_SIZE = 10 * 1024
    private val GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    private val gson = GsonBuilder()
        .setDateFormat(GSON_DATE_FORMAT)
        .create()

    @Provides
    @Singleton
    fun providesApp(): App {
        return application
    }

    @Provides
    @Singleton
    fun providesCacheDir(application: App): File {
        return application.getCacheDir()
    }

    /**
     *
     * OkHttpClient providers
     */

    @Provides
    @Named("HeaderClient")
    fun provideHeaderClient(cacheDir: File, application: App): OkHttpClient {
        val cookieJar =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(application.applicationContext))
        val builder = OkHttpClient.Builder()
            .cache(Cache(cacheDir, CACHE_SIZE.toLong()))
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .addHeader("Authorization", "Client-ID 2d086962f60c89e")
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
            .cookieJar(cookieJar)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.BUILD_TYPE.toLowerCase() == "debug") {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return builder.build()
    }

    /**
     *
     * Retrofit providers
     */

    @Provides
    @Singleton
    @Named("HeaderRetrofit")
    fun provideHeaderRetrofit(@Named("HeaderClient") client: Lazy<OkHttpClient>): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Urls.URL)
            .client(client.get())
            .build()
    }

    /*
        Repository providers
     */

    @Provides
    fun provideImgurRepository(@Named("HeaderRetrofit") retrofit: Retrofit): ImgurRepository {
        val services: ImgurServices = retrofit.create(ImgurServices::class.java)
        return ImgurRepository(services)
    }
}