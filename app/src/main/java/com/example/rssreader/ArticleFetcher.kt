package com.example.rssreader

import androidx.compose.runtime.mutableStateOf
import com.example.rssreader.dto.DataDto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.net.URL

private const val TAG = "ARTICLE_FETCHER"

object ArticleFetcher {
    private const val BASE_URL = "https://api.rss2json.com/v1/api.json"
    private const val BASE_URL_RSS = "$BASE_URL?rss_url="

    suspend fun fetch(feedActivity: FeedActivity) {
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
                            linkImage = it.thumbnail!!,
                            isRead = mutableStateOf(false)
                        )
                    }

            val newArticles = mutableListOf<Article>()
            articles.forEach { fetchedArticle ->
                if (!ArticleStore.articles.any { article ->
                        article.title == fetchedArticle.title
                    }) {
                    newArticles.add(fetchedArticle)
                }
            }
            ArticleStore.articles.addAll(newArticles)

            if (!feedActivity.dbSettedUp) {
                feedActivity.setupFirebaseDb()
                feedActivity.dbSettedUp = true
            }
        }
    }
}