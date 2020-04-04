package com.ajkelley.imguralbumproject.models

import com.google.gson.annotations.SerializedName

class Album {

    @SerializedName("id")
    var id: String = ""

    @SerializedName("title")
    var title: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("datetime")
    var datetime: Int = -1

    @SerializedName("cover")
    var cover: String = ""

    @SerializedName("cover_width")
    var coverWidth: Int = 0

    @SerializedName("cover_height")
    var coverHeight: Int = 0

    @SerializedName("account_url")
    var accountUrl: String = ""

    @SerializedName("account_id")
    var accountId: Int = -1

    @SerializedName("privacy")
    var privacy: String = ""

    @SerializedName("layout")
    var layout: String = ""

    @SerializedName("views")
    var views: Int = 0

    @SerializedName("link")
    var link: String = ""

    @SerializedName("favorite")
    var favorite: Boolean = false

    @SerializedName("nsfw")
    var nsfw: Boolean = false

    @SerializedName("section")
    var section: String = ""

    @SerializedName("order")
    var order: Int = 0

    @SerializedName("deleteHash")
    var deleteHash: String = ""

    @SerializedName("images_count")
    var imagesCount: Int = 0

    @SerializedName("images")
    lateinit var images: Array<Image>

    @SerializedName("in_gallery")
    var inGallery: Boolean = false
}