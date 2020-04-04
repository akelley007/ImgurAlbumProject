package com.ajkelley.imguralbumproject.repositories

import com.ajkelley.imguralbumproject.web.services.ImgurServices

class ImgurRepository(val imgurServices: ImgurServices): BaseRepository() {

    suspend fun getAlbums(query: String) = safeApiCall{imgurServices.getAlbums(query, "album")}


    /*
        unused functions that could be used later given further development of project
     */
    suspend fun getAlbumDetails(hash: String) = safeApiCall{imgurServices.getAlbumDetails(hash)}

    suspend fun getAlbumImages(hash: String) = safeApiCall{imgurServices.getAlbumImages(hash)}

    suspend fun getAlbumImageDetails(albumHash: String, imageHash: String) = safeApiCall{imgurServices.getAlbumImageDetails(albumHash, imageHash)}
}