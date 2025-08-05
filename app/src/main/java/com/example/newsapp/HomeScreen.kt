package com.example.newsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: NewsViewModel = viewModel()) {
    val articles = viewModel.articles
    val isLoading = viewModel.isLoading

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("US Headlines") })
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(contentPadding = padding) {
                items(articles) { article ->
                    HeadlineItem(article)
                }
            }
        }
    }
}


@Composable
fun HeadlineItem(article: Article) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
            .padding(8.dp)
        ) {
            if (!article.urlToImage.isNullOrBlank()) {
                Image(
                    painter = rememberAsyncImagePainter(article.urlToImage),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Column(
                modifier = Modifier
                .padding(5.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
                if (article.publishedAt.isNotBlank()) {
                    var dateTime = article.publishedAt.removeSuffix("Z").split("T")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text =  "Date: " + dateTime[0] + "    Time: " + dateTime[1],
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
