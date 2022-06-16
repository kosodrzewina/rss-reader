package com.example.rssreader.screens.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.rssreader.Article
import com.example.rssreader.ArticleStore

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleListItem(article: Article) {
    var isRead by remember {
        mutableStateOf(article.isRead)
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        onClick = {
            article.isRead = true
            isRead = true
        },
        backgroundColor = if (isRead) Color(0xFFBEBEBE) else Color.White
    ) {
        Row {
            Column {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(article.linkImage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(64.dp)
                    )
                }
                if (isRead) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            Column {
                Text(
                    text = article.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = article.description,
                    fontWeight = FontWeight.Thin,
                    fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                )
            }
        }
    }
}
