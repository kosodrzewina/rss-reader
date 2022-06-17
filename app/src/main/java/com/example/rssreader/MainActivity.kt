package com.example.rssreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import com.example.rssreader.screens.LoginScreen
import com.example.rssreader.ui.theme.RSSReaderTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private val auth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSReaderTheme {
//                navHostController = rememberNavController()
//                NavGraph(navHostController, this, auth)
                LoginScreen(auth = auth, mainActivity = this)
            }
        }
    }
}
