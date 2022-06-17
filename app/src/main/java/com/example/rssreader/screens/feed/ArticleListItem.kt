package com.example.rssreader.screens.feed

import android.util.Base64
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.rssreader.Article
import com.example.rssreader.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleListItem(
    article: Article,
    auth: FirebaseAuth,
    database: FirebaseDatabase,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        onClick = {
            article.isRead.value = true

            val encodedUserEmail =
                Base64.encodeToString(auth.currentUser?.email?.toByteArray(), Base64.NO_WRAP)
            val encodedTitle = Base64.encodeToString(article.title.toByteArray(), Base64.NO_WRAP)
            database.getReference(encodedUserEmail).child(encodedTitle)
                .setValue(article.isRead.value)
            val encodedLink =
                URLEncoder.encode(article.linkArticle, StandardCharsets.UTF_8.toString())

            navController.navigate(
                Screen.ArticleWebViewScreen.routeWithArgs(
                    article.title,
                    encodedLink
                )
            )
        },
        backgroundColor = if (article.isRead.value) Color(0xFFBEBEBE) else Color.White
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
                if (article.isRead.value) {
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
