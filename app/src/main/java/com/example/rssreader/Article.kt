package com.example.rssreader

import androidx.compose.runtime.MutableState

data class Article(
    val title: String,
    val description: String,
    val linkArticle: String,
    val linkImage: String,
    var isRead: MutableState<Boolean>
)