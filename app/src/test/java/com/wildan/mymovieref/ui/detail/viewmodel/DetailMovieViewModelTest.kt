package com.wildan.mymovieref.ui.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.haroldadmin.cnradapter.NetworkResponse
import com.nhaarman.mockitokotlin2.verify
import com.wildan.mymovieref.data.model.DetailMovie
import com.wildan.mymovieref.data.model.DetailTVSeries
import com.wildan.mymovieref.data.repository.FakeRemoteRepository
import com.wildan.mymovieref.data.repository.LocalRepository
import com.wildan.mymovieref.data.repository.RemoteRepository
import com.wildan.mymovieref.utils.Resource
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class DetailMovieViewModelTest {

    private lateinit var detailViewModel: DetailMovieViewModel
    private var expectedMovieTitle = "Hard Kill"
    private var expectedSeriesTitle = "Fear the Walking Dead"
    private var movieID = 724989
    private var TVID = 62286

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remoteRepository: RemoteRepository

    @Mock
    private lateinit var localRepository: LocalRepository

    @Mock
    private lateinit var moviesObserver: Observer<Resource<DetailMovie>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<DetailTVSeries>>

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        detailViewModel = DetailMovieViewModel(remoteRepository, localRepository)
    }

    @After
    @Throws(IOException::class)
    fun after() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun getDetailMovie() = runBlocking {
        detailViewModel.getDetailMovie(movieID).observeForever(moviesObserver)
        Mockito.`when`(remoteRepository.getDetailMovie(movieID))
            .thenReturn(FakeRemoteRepository.fakeSuccessDetailMovieSeriesResponse())

        verify(remoteRepository).getDetailMovie(movieID)
        Mockito.verify(moviesObserver).onChanged(Resource.loading(null))

        when (val data =
            remoteRepository.getDetailMovie(
                movieID
            )) {
            is NetworkResponse.Success -> {
                TestCase.assertNotNull(data.body)
                TestCase.assertEquals(expectedMovieTitle, data.body.title)
            }
        }
    }

    @Test
    fun getDetailTV() = runBlocking {
        detailViewModel.getDetailTV(TVID).observeForever(tvObserver)
        Mockito.`when`(remoteRepository.getDetailTVSeries(TVID))
            .thenReturn(FakeRemoteRepository.fakeSuccessDetailTVSeriesResponse())

        verify(remoteRepository).getDetailTVSeries(TVID)
        Mockito.verify(tvObserver).onChanged(Resource.loading(null))

        when (val data =
            remoteRepository.getDetailTVSeries(
                TVID
            )) {
            is NetworkResponse.Success -> {
                TestCase.assertNotNull(data.body)
                TestCase.assertEquals(expectedSeriesTitle, data.body.name)
            }
        }
    }
}