package com.example.rssreader.navigation

sealed class Screen(val route: String) {
    object FeedScreen : Screen(FEED_SCREEN)
    object ArticleWebViewScreen : Screen(ARTICLE_WEB_VIEW_SCREEN)

    fun routeWithArgs(vararg args: String): String = buildString {
        append(route)
        args.forEach { append("/$it") }
    }
}