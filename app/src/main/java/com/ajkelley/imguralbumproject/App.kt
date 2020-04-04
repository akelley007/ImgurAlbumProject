package com.ajkelley.imguralbumproject

import android.app.Application
import com.ajkelley.imguralbumproject.dependancyInjection.ApiComponent
import com.ajkelley.imguralbumproject.dependancyInjection.ApiModule
import com.ajkelley.imguralbumproject.dependancyInjection.DaggerApiComponent
import com.google.gson.GsonBuilder

//custom Application class for dependency injection
class App: Application() {
    companion object{
        lateinit var instance: App
        lateinit var apiComponent: ApiComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        apiComponent = DaggerApiComponent.builder()
            .apiModule(ApiModule(this))
            .build()
    }

}