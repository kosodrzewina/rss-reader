package com.example.rssreader

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rssreader.navigation.NavGraph
import com.example.rssreader.ui.theme.RSSReaderTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val TAG = "FEED_ACTIVITY"

class FeedActivity : ComponentActivity(), ValueEventListener {
    private lateinit var navHostController: NavHostController
    private val auth by lazy { Firebase.auth }
    private val database by lazy { Firebase.database }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (auth.currentUser == null) {
            goBackToLogin()
        }

        setContent {
            RSSReaderTheme {
                navHostController = rememberNavController()
                NavGraph(navHostController, auth, database, this)
            }
        }

        setupFirebaseDb()
    }

    private fun setupFirebaseDb() {
        val encodedUserEmail =
            Base64.encodeToString(auth.currentUser?.email?.toByteArray(), Base64.NO_WRAP)
        val feed = database.getReference(encodedUserEmail)

        feed.addValueEventListener(this)
    }

    private fun goBackToLogin() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        snapshot.children.forEach { dataSnapshot ->
            val title = String(Base64.decode(dataSnapshot.key, Base64.NO_WRAP))
            val isRead = dataSnapshot.value as Boolean

            try {
                ArticleStore.articles.first { article ->
                    article.title == title
                }.isRead.value = isRead
            } catch (e: NoSuchElementException) {
                Log.e(TAG, e.toString())
            }
        }
    }

    override fun onCancelled(error: DatabaseError) {
        Log.e(TAG, error.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        database.reference.removeEventListener(this)
    }
}
