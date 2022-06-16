package com.example.rssreader.screens.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rssreader.ArticleStore
import com.example.rssreader.EmptyView
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun ArticleList(auth: FirebaseAuth, database: FirebaseDatabase, modifier: Modifier = Modifier) {
    val viewModel: ArticleListViewModel = viewModel()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    viewModel.refresh(auth, database)

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.refresh(auth, database) }) {
        if (ArticleStore.articles.isEmpty()) {
            EmptyView(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
            ) {
                items(items = ArticleStore.articles) {
                    ArticleListItem(it, auth, database)
                }
            }
        }
    }
}