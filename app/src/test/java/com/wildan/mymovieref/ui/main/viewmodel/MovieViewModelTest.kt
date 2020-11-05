package com.wildan.mymovieref.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.espresso.IdlingRegistry
import com.haroldadmin.cnradapter.NetworkResponse
import com.nhaarman.mockitokotlin2.times
import com.wildan.mymovieref.data.model.PopularMovie
import com.wildan.mymovieref.data.model.PopularTVSeries
import com.wildan.mymovieref.data.repository.FakeRemoteRepository
import com.wildan.mymovieref.data.repository.RemoteRepository
import com.wildan.mymovieref.utils.EspressoIdlingResource
import com.wildan.mymovieref.utils.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MovieViewModelTest {

    private lateinit var mainViewModel: MovieViewModel
    private var expectedListSize = 20
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remoteRepository: RemoteRepository

    @Mock
    private lateinit var moviesObserver: Observer<Resource<List<PopularMovie>>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<List<PopularTVSeries>>>



    @Before
    fun setUP() {
        Dispatchers.setMain(testDispatcher)
        mainViewModel = MovieViewModel(remoteRepository)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    // menguji apakah list popular movie tidak null dan berjumlah 20
    @Test
    fun getMoviePopularList() = runBlocking {
        val dummyPopularMovie = FakeRemoteRepository.fakeSuccessPopularMoviesResponse()
        mainViewModel.getMoviePopularList(1).observeForever(moviesObserver)
        `when`(remoteRepository.getPopularMovies(1)).thenReturn(dummyPopularMovie)

        verify(remoteRepository).getPopularMovies(1)
        verify(moviesObserver).onChanged(Resource.loading(null))

        when (val data =
            remoteRepository.getPopularMovies(
                1
            )) {
            is NetworkResponse.Success -> {
                assertNotNull(data.body)
                assertEquals(expectedListSize, data.body.results.size)
            }
        }
    }


    // menguji apakah list popular TV tidak null dan berjumlah 10
    @Test
    fun getTVSeriesPopularList() = runBlocking {
        mainViewModel.getTVSeriesPopularList(1).observeForever(tvObserver)
        val dummyPopularTV = FakeRemoteRepository.fakeSuccessPopularTVSeriesResponse()
        `when`(remoteRepository.getPopularTVSeries(1)).thenReturn(dummyPopularTV)

        verify(remoteRepository).getPopularTVSeries(1)
        verify(tvObserver).onChanged(Resource.loading(null))
        when (val data =
            remoteRepository.getPopularTVSeries(
                1
            )) {
            is NetworkResponse.Success -> {
                assertNotNull(data.body)
                assertEquals(expectedListSize, data.body.results.size)
            }
        }
    }
}