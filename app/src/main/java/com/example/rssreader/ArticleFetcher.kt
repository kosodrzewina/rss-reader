package com.example.rssreader

import com.example.rssreader.dto.DataDto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.net.URL

object ArticleFetcher {
    private const val BASE_URL = "https://api.rss2json.com/v1/api.json"
    private const val BASE_URL_RSS = "$BASE_URL?rss_url="

    suspend fun fetch() {
        withContext(IO) {
            val response =
                URL(
                    BASE_URL_RSS + "https://moxie.foxnews.com/feedburner/scitech.xml"
                ).readText()
            val deserializedData = Gson().fromJson(response, DataDto::class.java)
            val articles =
                deserializedData
                    .items
                    .filter {
                        it.title != null &&
                                it.description != null &&
                                it.thumbnail != null &&
                                it.link != null
                    }
                    .map {
                        Article(
                            title = it.title!!,
                            description = it.description!!,
                            linkArticle = it.link!!,
                            linkImage = it.thumbnail!!
                        )
                    }

            ArticleStore.articles.apply {
                clear()
                addAll(articles)
            }
        }
    }
}