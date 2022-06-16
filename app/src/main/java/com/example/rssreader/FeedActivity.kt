package com.example.rssreader

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.rssreader.screens.feed.FeedScreen
import com.example.rssreader.ui.theme.RSSReaderTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FeedActivity : ComponentActivity() {
    private val auth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (auth.currentUser == null) {
            goBackToLogin()
        }

        setContent {
            RSSReaderTheme {
                FeedScreen(auth, this)
            }
        }
    }

    private fun goBackToLogin() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
