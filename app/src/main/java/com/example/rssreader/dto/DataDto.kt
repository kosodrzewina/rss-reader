package com.example.rssreader.dto

import com.google.gson.annotations.SerializedName

data class DataDto(
    @SerializedName("status") var status: String? = null,
    @SerializedName("feed") var feed: Feed? = Feed(),
    @SerializedName("items") var items: ArrayList<ItemDto> = arrayListOf()
)