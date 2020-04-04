package com.ajkelley.imguralbumproject.models

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

class Image {

    @SerializedName("id")
    var id: String = ""

    @SerializedName("title")
    var title: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("datetime")
    var datetime: Int = -1

    @SerializedName("type")
    var type: String = ""

    @SerializedName("animated")
    var animated: Boolean = false

    @SerializedName("width")
    var width: Int = 0

    @SerializedName("height")
    var height: Int = 0

    @SerializedName("size")
    var size: Int = 0

    @SerializedName("views")
    var views: Int = 0

    @SerializedName("bandwidth")
    lateinit var bandwidth: BigInteger

    @SerializedName("delete_hash")
    var deleteHash: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("section")
    var section: String = ""

    @SerializedName("link")
    var link: String = ""

    @SerializedName("gifv")
    var gifv: String = ""

    @SerializedName("mp4")
    var mp4: String = ""

    @SerializedName("mp4_size")
    var mp4Size: String = ""

    @SerializedName("looping")
    var looping: Boolean = false

    @SerializedName("favorite")
    var favorite: Boolean = false

    @SerializedName("nsfw")
    var nsfw: Boolean = false

    @SerializedName("vote")
    lateinit var vote: String

    @SerializedName("in_gallery")
    var inGallery: Boolean = false
}