package com.wildan.mymovieref.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.wildan.mymovieref.data.model.*
import com.wildan.mymovieref.data.remote.TheMovieDBAPI
import com.wildan.mymovieref.utils.Constants.API_KEY
import com.wildan.mymovieref.utils.EspressoIdlingResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(private val apiService: TheMovieDBAPI) {

    suspend fun getPopularMovies(page: Int): NetworkResponse<ResponseListObject<PopularMovie>, ErrorResponse> {
        EspressoIdlingResource.increment()
        when (val data =
            apiService.getPopularMovies(
                API_KEY, page
            )) {
            is NetworkResponse.Success -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.Success(data.body, null, 200)
            }
            is NetworkResponse.ServerError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.ServerError(data.body, data.code)
            }
            is NetworkResponse.NetworkError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.NetworkError(data.error)
            }
            is NetworkResponse.UnknownError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.UnknownError(data.error)
            }
        }
    }

    suspend fun getPopularTVSeries(page: Int): NetworkResponse<ResponseListObject<PopularTVSeries>, ErrorResponse> {
        EspressoIdlingResource.increment()
        when (val data =
            apiService.getPopularTVSeries(
                API_KEY, page
            )) {
            is NetworkResponse.Success -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.Success(data.body, null, 200)
            }
            is NetworkResponse.ServerError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.ServerError(data.body, data.code)
            }
            is NetworkResponse.NetworkError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.NetworkError(data.error)
            }
            is NetworkResponse.UnknownError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.UnknownError(data.error)
            }
        }
    }

    suspend fun getDetailMovie(movieID: Int): NetworkResponse<DetailMovie, ErrorResponse> {
        EspressoIdlingResource.increment()
        when (val data =
            apiService.getDetailMovie(
                movieID, API_KEY
            )) {
            is NetworkResponse.Success -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.Success(data.body, null, 200)
            }
            is NetworkResponse.ServerError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.ServerError(data.body, data.code)
            }
            is NetworkResponse.NetworkError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.NetworkError(data.error)
            }
            is NetworkResponse.UnknownError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.UnknownError(data.error)
            }
        }
    }

    suspend fun getDetailTVSeries(tvID: Int): NetworkResponse<DetailTVSeries, ErrorResponse> {
        EspressoIdlingResource.increment()
        when (val data =
            apiService.getDetailTV(
                tvID, API_KEY
            )) {
            is NetworkResponse.Success -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.Success(data.body, data.headers, 200)
            }
            is NetworkResponse.ServerError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.ServerError(data.body, data.code)
            }
            is NetworkResponse.NetworkError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.NetworkError(data.error)
            }
            is NetworkResponse.UnknownError -> {
                EspressoIdlingResource.decrement()
                return NetworkResponse.UnknownError(data.error)
            }
        }
    }

}