package com.example.rssreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rssreader.MainActivity
import com.example.rssreader.screens.LoginScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(navHostController: NavHostController, mainActivity: MainActivity, auth: FirebaseAuth) {
    NavHost(navController = navHostController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(auth, mainActivity, navHostController)
        }
    }
}
