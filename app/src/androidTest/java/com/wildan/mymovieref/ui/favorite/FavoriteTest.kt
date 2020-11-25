package com.wildan.mymovieref.ui.favorite


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.LivePagedListBuilder
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.wildan.mymovieref.data.local.FavoriteDao
import com.wildan.mymovieref.data.local.FavoriteDatabase
import com.wildan.mymovieref.data.repository.FakeLocalRepository
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoriteTest {

    private lateinit var favDao: FavoriteDao
    private lateinit var db: FavoriteDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onCreateDB() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, FavoriteDatabase::class.java)
            .allowMainThreadQueries().build()
        favDao = db.favoriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun testingInsertDataFavoriteMovietoDatabase() = runBlockingTest {
        // test insert data
        val dummyData = FakeLocalRepository.loadFavoriteMovies()
        for (data in dummyData) {
            favDao.addFavoriteMovie(data)
        }
        val checkValue = favDao.isInFavMovie(dummyData[0].id).getOrAwaitValue()
        TestCase.assertNotNull(checkValue)
    }

    @Test
    @Throws(IOException::class)
    fun testingInsertDataFavoriteTVtoDatabase() = runBlockingTest {
        // test insert data
        val dummyData = FakeLocalRepository.loadFavoriteTV()
        for (data in dummyData) {
            favDao.addFavoriteSeries(data)
        }
        val checkValue = favDao.isInFavSeries(dummyData[0].id).getOrAwaitValue()
        TestCase.assertNotNull(checkValue)
    }

    @Test
    @Throws(IOException::class)
    fun testingDeleteDataFavoriteMoviefromDatabase() = runBlockingTest {
        val dummyData = FakeLocalRepository.loadFavoriteMovies()
        for (data in dummyData) {
            favDao.addFavoriteMovie(data)
        }

        // hapus data ke 0
        favDao.deleteMovie(dummyData[0])
        val favMovies = LivePagedListBuilder(favDao.getFavMovies(), 10).build().getOrAwaitValue()
        assertThat(favMovies).doesNotContain(dummyData[0])
    }

    @Test
    @Throws(IOException::class)
    fun testingDeleteDataFavoriteTVfromDatabase() = runBlockingTest {
        val dummyData = FakeLocalRepository.loadFavoriteTV()
        for (data in dummyData) {
            favDao.addFavoriteSeries(data)
        }

        // hapus data ke 0
        favDao.deleteTV(dummyData[0])
        val favTV = LivePagedListBuilder(favDao.getFavSeries(), 10).build().getOrAwaitValue()
        assertThat(favTV).doesNotContain(dummyData[0])
    }

}