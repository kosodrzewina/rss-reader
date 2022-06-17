package com.example.rssreader.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssreader.ArticleFetcher
import com.example.rssreader.FeedActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleListViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh(feedActivity: FeedActivity) {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            withContext(CoroutineScope(IO).coroutineContext) {
                ArticleFetcher.fetch(feedActivity)
                _isRefreshing.emit(false)
            }
        }
    }
}