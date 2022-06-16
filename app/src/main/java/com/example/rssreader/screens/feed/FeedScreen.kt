package com.example.rssreader.screens.feed

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.rssreader.ArticleStore
import com.example.rssreader.FeedActivity
import com.example.rssreader.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun FeedScreen(auth: FirebaseAuth, database: FirebaseDatabase, feedActivity: FeedActivity) {
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Articles") },
                actions = {
                    IconButton(
                        onClick = {
                            auth.signOut()
                            feedActivity.startActivity(
                                Intent(
                                    feedActivity,
                                    MainActivity::class.java
                                )
                            )
                            ArticleStore.articles.clear()
                            feedActivity.finish()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                    }
                }
            )
        }
    ) {
        ArticleList(
            auth = auth,
            database = database,
            modifier = Modifier.padding(top = it.calculateTopPadding())
        )
    }
}
