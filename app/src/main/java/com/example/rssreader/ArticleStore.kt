package com.example.rssreader

import androidx.compose.runtime.mutableStateListOf

object ArticleStore {
    var articles = mutableStateListOf<Article>()
}