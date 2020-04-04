package com.ajkelley.imguralbumproject.viewModels

import androidx.lifecycle.MutableLiveData
import com.ajkelley.imguralbumproject.models.Album
import com.ajkelley.imguralbumproject.models.responses.GetAlbumsResponse
import com.ajkelley.imguralbumproject.repositories.ImgurRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumsListViewModel: BaseViewModel() {
    @Inject
    lateinit var imgurRepository: ImgurRepository

    init {
        inject()
    }

    var albums: MutableLiveData<ArrayList<Album>> = MutableLiveData()
    val selectedAlbum: MutableLiveData<Album> = MutableLiveData()
    var selectedAlbumCoverUrl: String = ""


    fun onAlbumSelected(album: Album, coverUrl: String){
        selectedAlbumCoverUrl = coverUrl
        this.selectedAlbum.value = album
    }

    //coroutine to fetch albums given a search query
    fun fetchAlbums(query: String){
        onRetrieveInfoStart()
        CoroutineScope(Dispatchers.IO).launch {
            val response: GetAlbumsResponse? = imgurRepository.getAlbums(query)

            //set the value of albums to the response to trigger ui update
            withContext(Dispatchers.Main){
                if(response != null){
                    albums.value = response.albums
                }
                else{
                    //result was null, reset albums to empty list and trigger loading end
                    albums.value = ArrayList()
                    onRetrieveInfoFinish()
                }
            }
        }
    }

}