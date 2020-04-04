package com.ajkelley.imguralbumproject.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajkelley.imguralbumproject.App
import com.ajkelley.imguralbumproject.dependancyInjection.ApiComponent
import com.ajkelley.imguralbumproject.dependancyInjection.ApiModule
import com.ajkelley.imguralbumproject.dependancyInjection.DaggerApiComponent

open class BaseViewModel: ViewModel() {
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    init {
        inject()
        loadingVisibility.value = View.GONE
    }

    fun onRetrieveInfoStart(){
        loadingVisibility.value = View.VISIBLE
    }

    fun onRetrieveInfoFinish(){
        loadingVisibility.value = View.GONE
    }


    //base inject method, can add when cases to inject multiple types of fragments which will be automatically inject on init
    fun inject() {
        when (this) {
            is AlbumsListViewModel -> App.apiComponent.inject(this)
        }
    }
}