package com.wildan.mymovieref.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.wildan.mymovieref.data.model.PopularMovie
import com.wildan.mymovieref.data.model.PopularTVSeries
import com.wildan.mymovieref.data.remote.TheMovieDBAPI
import com.wildan.mymovieref.data.repository.FakeRemoteRepository
import com.wildan.mymovieref.data.repository.RemoteRepository
import com.wildan.mymovieref.ui.MainCoroutineRule
import com.wildan.mymovieref.utils.Constants
import com.wildan.mymovieref.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MovieViewModelTest {

    private lateinit var mainViewModel: MovieViewModel
    private var expectedListSize = 20

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var theMovieDBAPI: TheMovieDBAPI

    @Mock
    private lateinit var remoteRepository: RemoteRepository

    @Mock
    private lateinit var moviesObserver: Observer<Resource<List<PopularMovie>>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<List<PopularTVSeries>>>

    @Before
    fun setViewModel() {
//        remoteRepository = RemoteRepository(theMovieDBAPI)
        mainViewModel = MovieViewModel(remoteRepository)
    }

    // menguji apakah list popular movie tidak null dan berjumlah 20
    @Test
    fun getMoviePopularList() = mainCoroutineRule.runBlockingTest {
        val dummyPopularMovie = FakeRemoteRepository.fakeSuccessPopularMoviesResponse()
        `when`(remoteRepository.getPopularMovies(1)).thenReturn(dummyPopularMovie)
//        Mockito.doReturn(dummyPopularMovie).`when`(remoteRepository).getPopularMovies(1)
        verify(remoteRepository).getPopularMovies(1)
        val popularMovies = mainViewModel.getMoviePopularList(1).value
        assertNotNull(popularMovies)
        assertEquals(expectedListSize, popularMovies?.data?.size)

        mainViewModel.getMoviePopularList(1).observeForever(moviesObserver)
        verify(moviesObserver).onChanged(popularMovies)
    }

    // menguji apakah list popular TV tidak null dan berjumlah 10
//    @Test
//    fun getTVSeriesPopularList() {
//        val popularSeries = mainViewModel.getTVSeriesPopularList()
//        assertNotNull(popularSeries)
//        assertEquals(expectedListSize, popularSeries.size)
//    }
}