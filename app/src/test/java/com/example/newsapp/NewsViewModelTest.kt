package com.example.newsapp

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    private val api: NewsApiService = mockk()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: NewsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockkObject(RetrofitInstance){
            every { RetrofitInstance.api } returns api
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `fetch news and sort articles`() = runTest {
        val mockArticles = listOf(
            Article("A", "description A", "content A", "urlA", "2025-08-05T12:00:00Z"),
            Article("B", "description B", "content B", "urlB", "2025-08-06T12:00:00Z")
        )

        val mockResponse = NewsModels("status", mockArticles.size, mockArticles)

        coEvery { api.getTopHeadlines(any(), any()) } returns mockResponse

        viewModel = NewsViewModel(api, dispatcher)

        dispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.isLoading)
        assertEquals(2, viewModel.articles.size)
        assertEquals("B", viewModel.articles[0].title)
        assertEquals("A", viewModel.articles[1].title)
    }
}