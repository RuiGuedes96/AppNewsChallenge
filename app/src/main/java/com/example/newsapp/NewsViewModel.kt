package com.example.newsapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    var articles by mutableStateOf<List<Article>>(emptyList())

    var isLoading by mutableStateOf(true)

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTopHeadlines(
                    "us",
                    "e89ff91542b14b1999068ec81b225771"
                )
                articles = response.articles.sortedByDescending { it.publishedAt }
            } catch (e: Exception) {
                Log.d("NEWS_API", "Exception: $e")
            } finally {
                isLoading = false
            }
        }
    }
}