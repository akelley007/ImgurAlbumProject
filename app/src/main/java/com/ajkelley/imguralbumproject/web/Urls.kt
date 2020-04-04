package com.ajkelley.imguralbumproject.web

//url const values
class Urls {
    companion object{
        const val URL = "https://api.imgur.com/3/"
        const val ALBUMS_URL = "gallery/search"
        const val ALBUM_DETAILS_URL = "album/{albumHash}"
        const val ALBUM_IMAGES_URL = "album/{albumHash}/images"
        const val ALBUM_IMAGE_URL = "album/{albumHash}/image/{imageHash}"

        //url items
        const val ALBUM_HASH = "albumHash"
        const val ALBUM_IMAGE_HASH = "imageHash"
        const val QUERY = "q_all"
        const val TYPE = "q_type"
    }

}