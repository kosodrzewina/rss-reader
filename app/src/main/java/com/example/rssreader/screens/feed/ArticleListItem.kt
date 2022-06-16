package com.example.rssreader.screens.feed

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.rssreader.Article

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleListItem(article: Article, onClick: () -> Unit) {
    Card(shape = RoundedCornerShape(16.dp), elevation = 8.dp, onClick = onClick) {
        Text(text = article.title)
    }
}
