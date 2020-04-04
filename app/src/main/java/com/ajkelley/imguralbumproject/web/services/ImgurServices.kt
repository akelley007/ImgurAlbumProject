package com.ajkelley.imguralbumproject.web.services

import com.ajkelley.imguralbumproject.models.Album
import com.ajkelley.imguralbumproject.models.Image
import com.ajkelley.imguralbumproject.models.responses.GetAlbumsResponse
import com.ajkelley.imguralbumproject.web.Urls
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImgurServices {
    @GET(Urls.ALBUMS_URL)
    suspend fun getAlbums(@Query(Urls.QUERY) query: String, @Query(Urls.TYPE) type: String): Response<GetAlbumsResponse>

    /*
        Unused endpoints but here in case of future development
     */

    @GET(Urls.ALBUM_DETAILS_URL)
    suspend fun getAlbumDetails(@Path(Urls.ALBUM_HASH) hash: String): Response<Album>

    @GET(Urls.ALBUM_IMAGES_URL)
    suspend fun getAlbumImages(@Path(Urls.ALBUM_HASH) hash: String): Response<ArrayList<Image>>

    @GET(Urls.ALBUM_IMAGE_URL)
    suspend fun getAlbumImageDetails(@Path(Urls.ALBUM_HASH) albumHash: String, @Path(Urls.ALBUM_IMAGE_HASH) imageHash: String): Response<Image>
}