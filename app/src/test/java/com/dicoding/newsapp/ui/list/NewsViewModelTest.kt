package com.dicoding.newsapp.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.newsapp.data.NewsRepository
import com.dicoding.newsapp.data.local.entity.NewsEntity
import com.dicoding.newsapp.utils.DataDummy
import com.dicoding.newsapp.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    val instanstExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsViewModel: NewsViewModel
    private val dummyNews = DataDummy.generateDummyNewsEntity()

    @Before
    fun setUp() {
        newsViewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun `when Get HeadlineNews Should Not Null and Return Success`() {
        val observer = Observer<com.dicoding.newsapp.data.Result<List<NewsEntity>>> {}
        try {
            val expectedNews = MutableLiveData<com.dicoding.newsapp.data.Result<List<NewsEntity>>>()
            expectedNews.value = com.dicoding.newsapp.data.Result.Success(dummyNews)
            `when`(newsRepository.getHeadlineNews()).thenReturn(expectedNews)

            val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()
            Mockito.verify(newsRepository).getHeadlineNews()
            Assert.assertNotNull(actualNews)
            Assert.assertTrue(actualNews is com.dicoding.newsapp.data.Result.Success)
            Assert.assertEquals(dummyNews.size, (actualNews as com.dicoding.newsapp.data.Result.Success).data.size)
        } finally {
            newsViewModel.getHeadlineNews().removeObserver(observer)
        }
    }
}