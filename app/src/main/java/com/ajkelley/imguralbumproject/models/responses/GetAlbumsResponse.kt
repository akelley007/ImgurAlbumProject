package com.ajkelley.imguralbumproject.models.responses

import com.ajkelley.imguralbumproject.models.Album
import com.google.gson.annotations.SerializedName

class GetAlbumsResponse {
    @SerializedName("data")
    lateinit var albums: ArrayList<Album>
}