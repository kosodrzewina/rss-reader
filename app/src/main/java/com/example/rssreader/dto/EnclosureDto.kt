package com.example.rssreader.dto

import com.google.gson.annotations.SerializedName

data class EnclosureDto(
    @SerializedName("link") var link: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null
)