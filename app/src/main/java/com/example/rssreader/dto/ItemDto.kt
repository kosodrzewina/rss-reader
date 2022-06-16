package com.example.rssreader.dto

import com.google.gson.annotations.SerializedName

data class ItemDto(
    @SerializedName("title") var title: String? = null,
    @SerializedName("pubDate") var pubDate: String? = null,
    @SerializedName("link") var link: String? = null,
    @SerializedName("guid") var guid: String? = null,
    @SerializedName("author") var author: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("enclosure") var enclosure: EnclosureDto? = EnclosureDto(),
    @SerializedName("categories") var categories: ArrayList<String> = arrayListOf()
)
