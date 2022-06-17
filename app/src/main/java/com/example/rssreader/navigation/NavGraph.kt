package com.example.rssreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rssreader.FeedActivity
import com.example.rssreader.screens.ArticleWebViewScreen
import com.example.rssreader.screens.feed.FeedScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun NavGraph(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    database: FirebaseDatabase,
    feedActivity: FeedActivity
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.FeedScreen.route
    ) {
        composable(FEED_SCREEN) {
            FeedScreen(
                auth = auth,
                database = database,
                feedActivity = feedActivity,
                navController = navHostController
            )
        }

        composable(
            route = "$ARTICLE_WEB_VIEW_SCREEN/{title}/{link}",
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("link") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            val title = it.arguments?.getString("title")
            val link = it.arguments?.getString("link")

            if (title != null && link != null) {
                val decodedLink = URLDecoder.decode(link, StandardCharsets.UTF_8.toString())

                ArticleWebViewScreen(
                    title = title,
                    link = decodedLink,
                    navController = navHostController
                )
            }
        }
    }
}
