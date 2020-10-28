package com.wildan.mymovieref.data.repository

import com.wildan.mymovieref.data.remote.TheMovieDBAPI
import com.wildan.mymovieref.utils.Constants.API_KEY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(private val apiService: TheMovieDBAPI) {

    suspend fun getPopularMovies(page: Int) = apiService.getPopularMovies(API_KEY, page)

    suspend fun getPopularTVSeries(page: Int) =
        apiService.getPopularTVSeries(API_KEY, page)

    suspend fun getDetailMovie(movieID: Int) = apiService.getDetailMovie(movieID, API_KEY)

    suspend fun getDetailTVSeries(tvID: Int) = apiService.getDetailTV(tvID, API_KEY)

}