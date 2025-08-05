package com.example.newsapp

data class NewsModels (
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article (
    val title: String,
    val urlToImage: String,
    val publishedAt: String
)