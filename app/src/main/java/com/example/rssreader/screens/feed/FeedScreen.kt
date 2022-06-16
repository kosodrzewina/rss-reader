package com.example.rssreader.screens.feed

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.rssreader.FeedActivity
import com.example.rssreader.MainActivity
import com.google.firebase.auth.FirebaseAuth

@Composable
fun FeedScreen(auth: FirebaseAuth, feedActivity: FeedActivity) {
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
                            feedActivity.finish()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                    }
                }
            )
        }
    ) {
        ArticleList(modifier = Modifier.padding(top = it.calculateTopPadding()))
    }
}
