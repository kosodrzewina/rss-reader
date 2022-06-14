package com.example.rssreader.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen(LOGIN_SCREEN)
}